package com.ssafy.businesscard.privateAlbum.dto;

import com.ssafy.businesscard.mycard.entity.Businesscard;
import java.io.Serializable;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PrivateAlbumResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long cardId;
    private String name;
    private String company;
    private String position;
    private String rank;
    private String department;
    private String email;
    private String landlineNumber;
    private String faxNumber;
    private String phoneNumber;
    private String address;
    private String realPicture;
    private Businesscard.Status frontBack;
    private String domainUrl;
    private String memo;

    // Getter 메서드 필요
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivateAlbumResponseDto)) return false;
        PrivateAlbumResponseDto that = (PrivateAlbumResponseDto) o;
        return Objects.equals(cardId, that.cardId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(company, that.company) &&
                Objects.equals(position, that.position) &&
                Objects.equals(rank, that.rank) &&
                Objects.equals(department, that.department) &&
                Objects.equals(email, that.email) &&
                Objects.equals(landlineNumber, that.landlineNumber) &&
                Objects.equals(faxNumber, that.faxNumber) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(address, that.address) &&
                Objects.equals(realPicture, that.realPicture) &&
                Objects.equals(frontBack, that.frontBack) &&
                Objects.equals(domainUrl, that.domainUrl) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, name, company, position, rank, department, email,
                landlineNumber, faxNumber, phoneNumber, address,
                realPicture, frontBack, domainUrl, memo);
    }
}
