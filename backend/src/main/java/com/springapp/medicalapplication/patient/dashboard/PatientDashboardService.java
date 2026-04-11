package com.springapp.medicalapplication.patient.dashboard;


import com.springapp.medicalapplication.consultation.ConsultationService;
import com.springapp.medicalapplication.consultation.dto.ConsultationResponseDTO;
import com.springapp.medicalapplication.patient.PatientService;
import com.springapp.medicalapplication.patient.dto.PatientResponseDTO;
import com.springapp.medicalapplication.patient.dashboard.PatientDashboardDTO;
import com.springapp.medicalapplication.patient.dashboard.PatientSummaryDTO;
import com.springapp.medicalapplication.prescription.PrescriptionService;
import com.springapp.medicalapplication.prescription.dto.PrescriptionResponseDTO;
import com.springapp.medicalapplication.referral.ReferralService;
import com.springapp.medicalapplication.referral.dto.ReferralResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PatientDashboardService {

    private final PatientService patientService;
    private final PrescriptionService prescriptionService;
    private final ReferralService referralService;
    private final ConsultationService consultationService;

    public PatientDashboardService(PatientService patientService,
                                   PrescriptionService prescriptionService,
                                   ReferralService referralService,
                                   ConsultationService consultationService) {
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.referralService = referralService;
        this.consultationService = consultationService;
    }

    public PatientDashboardDTO getDashboardByEmail(String email) {
        PatientResponseDTO patient = patientService.getPatientByEmail(email)
                .orElseThrow(() -> new RuntimeException("Pacientul nu a fost găsit!"));

        Long patientId = patient.id;

        List<ConsultationResponseDTO> consultations =
                consultationService.getConsultationsByPatientId(patientId);

        List<PrescriptionResponseDTO> prescriptions =
                prescriptionService.getPrescriptionsByPatientId(patientId);

        List<ReferralResponseDTO> referrals =
                referralService.getReferralsByPatientId(patientId);

        // ultimele 5 consultații, ordonate descrescător dacă DTO-ul are dată
        List<ConsultationResponseDTO> recentConsultations = consultations.stream()
                .sorted(Comparator.comparing(
                        ConsultationResponseDTO::getDate,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .limit(5)
                .toList();

        PatientSummaryDTO summary = new PatientSummaryDTO();
        summary.consultationsCount = consultations.size();
        summary.prescriptionsCount = prescriptions.size();
        summary.referralsCount = referrals.size();
        summary.totalDocumentsCount = prescriptions.size() + referrals.size();

        PatientDashboardDTO dashboard = new PatientDashboardDTO();
        dashboard.patient = patient;
        dashboard.summary = summary;
        dashboard.recentConsultations = recentConsultations;
        dashboard.prescriptions = prescriptions;
        dashboard.referrals = referrals;

        return dashboard;
    }
}