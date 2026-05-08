package com.springapp.medicalapplication.doctor.dashboard;

import com.springapp.medicalapplication.consultation.ConsultationService;
import com.springapp.medicalapplication.consultation.dto.ConsultationResponseDTO;
import com.springapp.medicalapplication.doctor.Doctor;
import com.springapp.medicalapplication.doctor.DoctorService;
import com.springapp.medicalapplication.doctor.dto.DoctorResponseDTO;
import com.springapp.medicalapplication.patient.PatientService;
import com.springapp.medicalapplication.patient.dashboard.PatientDashboardDTO;
import com.springapp.medicalapplication.patient.dashboard.PatientSummaryDTO;
import com.springapp.medicalapplication.patient.dto.PatientResponseDTO;
import com.springapp.medicalapplication.prescription.PrescriptionService;
import com.springapp.medicalapplication.prescription.dto.PrescriptionResponseDTO;
import com.springapp.medicalapplication.referral.ReferralService;
import com.springapp.medicalapplication.referral.dto.ReferralResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DoctorDashboardService {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final PrescriptionService prescriptionService;
    private final ReferralService referralService;
    private final ConsultationService consultationService;

    public DoctorDashboardService(DoctorService doctorService, PatientService patientService,
                                   PrescriptionService prescriptionService,
                                   ReferralService referralService,
                                   ConsultationService consultationService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.referralService = referralService;
        this.consultationService = consultationService;
    }

    public DoctorDashboardDTO getDashboardByEmail(String email) {
        DoctorResponseDTO doctor = doctorService.getDoctorByEmail(email)
                .orElseThrow(() -> new RuntimeException("Medicul nu a fost găsit!"));

        Long doctorId = doctor.id;

        List <PatientResponseDTO> patients = patientService.getPatientByFamilyDoctorId(doctorId);

        List<ConsultationResponseDTO> consultations =
                consultationService.getConsultationsByDoctorId(doctorId);

        List<PrescriptionResponseDTO> prescriptions =
                prescriptionService.getPrescriptionsByDoctorId(doctorId);

        List<ReferralResponseDTO> referrals =
                referralService.getReferralsByDoctorId(doctorId);

        // ultimele cateva consultatii ordonate desc dupa data
        List<ConsultationResponseDTO> recentConsultations = consultations.stream()
                .sorted(Comparator.comparing(
                        ConsultationResponseDTO::getDate,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .limit(10)
                .toList();

        DoctorSummaryDTO summary = new DoctorSummaryDTO();
        summary.patientsCount = patients.size();
        summary.consultationsCount = consultations.size();
        summary.prescriptionsCount = prescriptions.size();
        summary.referralsCount = referrals.size();
        summary.totalDocumentsCount = prescriptions.size() + referrals.size();

        DoctorDashboardDTO dashboard = new DoctorDashboardDTO();
        dashboard.doctor = doctor;
        dashboard.patients = patients;
        dashboard.summary = summary;
        dashboard.recentConsultations = recentConsultations;
        dashboard.prescriptions = prescriptions;
        dashboard.referrals = referrals;

        return dashboard;
    }

}
