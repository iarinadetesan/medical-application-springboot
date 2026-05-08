package com.springapp.medicalapplication.scheduler;


import com.springapp.medicalapplication.prescription.PrescriptionStatusService;
import com.springapp.medicalapplication.referral.ReferralStatusService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MedicalDocumentStatusScheduler {

    private final PrescriptionStatusService prescriptionStatusService;
    private final ReferralStatusService referralStatusService;

    public MedicalDocumentStatusScheduler(
            PrescriptionStatusService prescriptionStatusService,
            ReferralStatusService referralStatusService
    ) {
        this.prescriptionStatusService = prescriptionStatusService;
        this.referralStatusService = referralStatusService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(fixedRate = 60000) pt testare, a mers
    public void updateExpiredMedicalDocuments() {
        prescriptionStatusService.updateExpiredPrescriptions();
        referralStatusService.updateExpiredReferrals();
    }
}
