package com.service.documentation.util;

import com.service.documentation.enums.AppealStatus;
import com.service.documentation.enums.MoneyCurrency;
import com.service.documentation.enums.TransactionStatus;
import com.service.documentation.exception.ApiException;
import jakarta.servlet.http.HttpServletResponse;

public class CheckEnums {

    public static void validateMoneyCurrency(String enumString) {
        for (MoneyCurrency type : MoneyCurrency.values()) {
            if (type.name().equals(enumString)) {
                return;
            }
        }
        throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "The provided value " + enumString + " does not match any existing MoneyCurrency");
    }

    public static void validateTransactionStatus(String enumString) {
        for (TransactionStatus type : TransactionStatus.values()) {
            if (type.name().equals(enumString)) {
                return;
            }
        }
        throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "The provided value " + enumString + " does not match any existing TransactionStatus");
    }

    public static void validateAppealStatus(String enumString) {
        for (AppealStatus type : AppealStatus.values()) {
            if (type.name().equals(enumString)) {
                return;
            }
        }
        throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "The provided value " + enumString + " does not match any existing AppealStatus");
    }
}