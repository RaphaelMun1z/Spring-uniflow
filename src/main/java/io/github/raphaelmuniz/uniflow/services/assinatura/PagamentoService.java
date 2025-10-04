package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.PagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.RegistrarPagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.assinatura.Pagamento;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusPagamentoEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.PagamentoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.services.usuario.AssinanteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final AssinanteRepository assinanteRepository;
    private final AssinaturaModeloRepository assinaturaModeloRepository;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;
    private final AssinanteService assinanteService;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            AssinanteRepository assinanteRepository,
            AssinaturaModeloRepository assinaturaModeloRepository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            AssinanteService assinanteService) {
        this.pagamentoRepository = pagamentoRepository;
        this.assinanteRepository = assinanteRepository;
        this.assinaturaModeloRepository = assinaturaModeloRepository;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.assinanteService = assinanteService;
    }

    @Transactional
    public PagamentoResponseDTO processarNovoPagamento(PagamentoRequestDTO dto) {
        Assinante assinante = assinanteRepository.findById(dto.assinanteId())
                .orElseThrow(() -> new NotFoundException("Assinante não encontrado com o ID: " + dto.assinanteId()));

        AssinaturaModelo modelo = assinaturaModeloRepository.findById(dto.assinaturaModeloId())
                .orElseThrow(() -> new NotFoundException("Plano de assinatura não encontrado com o ID: " + dto.assinaturaModeloId()));

        if (assinanteService.verificarSePossuiAssinaturaAtiva(assinante.getId())) {
            throw new BusinessException("Não é possível processar o pagamento, pois o usuário já possui uma assinatura ativa.");
        }

        AssinaturaUsuario novaAssinatura = new AssinaturaUsuario();
        novaAssinatura.setAssinante(assinante);
        novaAssinatura.setAssinaturaModelo(modelo);
        novaAssinatura.setStatus(StatusAssinaturaUsuarioEnum.ATIVA);
        novaAssinatura.setDataInicio(LocalDateTime.now());
        novaAssinatura.setDataFim(LocalDateTime.now().plusMonths(modelo.getDuracaoEmMeses()));
        AssinaturaUsuario assinaturaSalva = assinaturaUsuarioRepository.save(novaAssinatura);

        Pagamento pagamento = new Pagamento();
        pagamento.setAssinaturaUsuario(assinaturaSalva);
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setValor(modelo.getPreco());
        pagamento.setStatus(StatusPagamentoEnum.APROVADO);

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        return toResponseDTO(pagamentoSalvo);
    }

    @Transactional
    public PagamentoResponseDTO registrarPagamentoExterno(RegistrarPagamentoRequestDTO dto) {
        AssinaturaUsuario assinatura = assinaturaUsuarioRepository.findById(dto.assinaturaUsuarioId())
                .orElseThrow(() -> new NotFoundException("Assinatura de usuário não encontrada com o ID: " + dto.assinaturaUsuarioId()));

        Pagamento pagamento = new Pagamento();
        pagamento.setAssinaturaUsuario(assinatura);
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setValor(dto.valor());
        pagamento.setStatus(dto.status());
        pagamento.setMetodo(dto.metodo());
        pagamento.setProtocolo(dto.protocolo());

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        return toResponseDTO(pagamentoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<PagamentoResponseDTO> listarHistoricoDePagamentos(String assinanteId, Pageable pageable) {
        if (!assinanteRepository.existsById(assinanteId)) {
            throw new NotFoundException("Assinante não encontrado com o ID: " + assinanteId);
        }

        Page<Pagamento> pagamentos = pagamentoRepository.findByAssinaturaUsuario_Assinante_IdOrderByDataPagamentoDesc(assinanteId, pageable);

        return pagamentos.map(this::toResponseDTO);
    }

    private PagamentoResponseDTO toResponseDTO(Pagamento pagamento) {
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getDataPagamento(),
                pagamento.getValor(),
                pagamento.getStatus(),
                pagamento.getMetodo(),
                pagamento.getProtocolo(),
                pagamento.getAssinaturaUsuario().getAssinante().getId()
        );
    }
}