package de.dtonal.payroll.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dtonal.payroll.model.VerificationToken;

public interface VerifcationTokenRepository extends JpaRepository<VerificationToken, Long> {

}
