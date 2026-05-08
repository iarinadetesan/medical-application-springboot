package com.springapp.medicalapplication.referral;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReferralStatusService {

    private final ReferralRepository referralRepository;

    public ReferralStatusService(ReferralRepository referralRepository) {
        this.referralRepository = referralRepository;
    }

    @Transactional
    public void updateExpiredReferrals() {
        LocalDate today = LocalDate.now();

        List<Referral> expiredReferrals =
                referralRepository.findByStatusAndExpiryDateBefore(
                        ReferralStatus.PENDING,
                        today
                );

        for (Referral referral : expiredReferrals) {
            referral.setStatus(ReferralStatus.EXPIRED);
        }

        referralRepository.saveAll(expiredReferrals);
    }
}