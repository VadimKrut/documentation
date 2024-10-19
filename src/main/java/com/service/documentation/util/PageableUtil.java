package com.service.documentation.util;

import com.service.documentation.enums.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction.ASC;

public class PageableUtil {

    public static Pageable generatePageable(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageNum >= 0 && pageSize != null && pageSize > 0) {
            return PageRequest.of(pageNum, pageSize);
        }
        return PageRequest.of(0, 100);
    }

    public static Pageable generatePageable(Integer pageNum, Integer pageSize, String sortField, SortDirection sortDirection) {
        String[] sortFields = null;
        if (sortField == null || sortField.isBlank()) {
            return generatePageable(pageNum, pageSize);
        } else {
            if (sortField.contains(",")) {
                sortFields = sortField.replaceAll(" ", "").split(",");
            }
        }
        if (pageNum != null && pageNum >= 0 && pageSize != null && pageSize > 0) {
            if (sortFields != null) {
                return PageRequest.of(pageNum, pageSize, (sortDirection == null) ? ASC : Sort.Direction.valueOf(sortDirection.getValue()), sortFields);
            } else {
                return PageRequest.of(pageNum, pageSize, (sortDirection == null) ? ASC : Sort.Direction.valueOf(sortDirection.getValue()), sortField);
            }
        }

        if (sortFields != null) {
            return PageRequest.of(0, 100, (sortDirection == null) ? ASC : Sort.Direction.valueOf(sortDirection.getValue()), sortFields);
        } else {
            return PageRequest.of(0, 100, (sortDirection == null) ? ASC : Sort.Direction.valueOf(sortDirection.getValue()), sortField);
        }
    }
}