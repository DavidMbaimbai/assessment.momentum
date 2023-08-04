package com.assessment.listener;

import com.assessment.enums.WithdrawalStatus;
import com.assessment.entity.WithdrawalProcess;

public class WithdrawalProcessEvent {
        private final WithdrawalProcess withdrawalProcess;
        private final WithdrawalStatus newStatus;

        public WithdrawalProcessEvent(Object source, WithdrawalProcess withdrawalProcess, WithdrawalStatus newStatus) {
            super();
            this.withdrawalProcess = withdrawalProcess;
            this.newStatus = newStatus;
        }

        public WithdrawalProcess getWithdrawalProcess() {
            return withdrawalProcess;
        }

        public WithdrawalStatus getNewStatus() {
            return newStatus;
        }
    }


