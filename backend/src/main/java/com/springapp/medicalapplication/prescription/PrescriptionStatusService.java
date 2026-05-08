package com.springapp.medicalapplication.prescription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrescriptionStatusService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionStatusService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Transactional
    public void updateExpiredPrescriptions() {
        LocalDate today = LocalDate.now();

        List<Prescription> expiredPrescriptions =
                prescriptionRepository.findByStatusAndExpiryDateBefore(
                        PrescriptionStatus.ACTIVE,
                        today
                );

        for (Prescription prescription : expiredPrescriptions) {
            prescription.setStatus(PrescriptionStatus.EXPIRED);
        }

        prescriptionRepository.saveAll(expiredPrescriptions);
    }
}