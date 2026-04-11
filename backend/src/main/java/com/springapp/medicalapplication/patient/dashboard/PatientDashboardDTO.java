package com.springapp.medicalapplication.patient.dashboard;

import com.springapp.medicalapplication.consultation.dto.ConsultationResponseDTO;
import com.springapp.medicalapplication.patient.dto.PatientResponseDTO;
import com.springapp.medicalapplication.prescription.dto.PrescriptionResponseDTO;
import com.springapp.medicalapplication.referral.dto.ReferralResponseDTO;

import java.util.List;

public class PatientDashboardDTO {
    public PatientResponseDTO patient;
    public PatientSummaryDTO summary;
    public List<ConsultationResponseDTO> recentConsultations;
    public List<PrescriptionResponseDTO> prescriptions;
    public List<ReferralResponseDTO> referrals;
}
