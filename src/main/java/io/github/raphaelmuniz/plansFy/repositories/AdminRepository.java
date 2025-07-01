package io.github.raphaelmuniz.plansFy.repositories;

import io.github.raphaelmuniz.plansFy.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}
