package cm.yowyob.bus_station_backend.infrastructure.outbound.persistence.mapper;

import cm.yowyob.bus_station_backend.domain.model.Organization;
import cm.yowyob.bus_station_backend.infrastructure.outbound.persistence.entity.OrganizationEntity;
import org.springframework.stereotype.Component;

@Component
public class OrganizationPersistenceMapper {

    public Organization toDomain(OrganizationEntity entity) {
        if (entity == null) return null;

        return Organization.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .organizationId(entity.getOrganizationId())
                .businessDomains(entity.getBusinessDomains())
                .email(entity.getEmail())
                .shortName(entity.getShortName())
                .longName(entity.getLongName())
                .description(entity.getDescription())
                .logoUrl(entity.getLogoUrl())
                .isIndividualBusiness(entity.isIndividualBusiness())
                .legalForm(entity.getLegalForm())
                .isActive(entity.isActive())
                .websiteUrl(entity.getWebsiteUrl())
                .socialNetwork(entity.getSocialNetwork())
                .businessRegistrationNumber(entity.getBusinessRegistrationNumber())
                .taxNumber(entity.getTaxNumber())
                .capitalShare(entity.getCapitalShare())
                .registrationDate(entity.getRegistrationDate())
                .ceoName(entity.getCeoName())
                .yearFounded(entity.getYearFounded())
                .keywords(entity.getKeywords())
                .status(entity.getStatus())
                .build();
    }

    public OrganizationEntity toEntity(Organization domain) {
        if (domain == null) return null;

        return OrganizationEntity.builder()
                .id(domain.getId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .deletedAt(domain.getDeletedAt())
                .createdBy(domain.getCreatedBy())
                .updatedBy(domain.getUpdatedBy())
                .organizationId(domain.getOrganizationId())
                .businessDomains(domain.getBusinessDomains())
                .email(domain.getEmail())
                .shortName(domain.getShortName())
                .longName(domain.getLongName())
                .description(domain.getDescription())
                .logoUrl(domain.getLogoUrl())
                .isIndividualBusiness(domain.isIndividualBusiness())
                .legalForm(domain.getLegalForm())
                .isActive(domain.isActive())
                .websiteUrl(domain.getWebsiteUrl())
                .socialNetwork(domain.getSocialNetwork())
                .businessRegistrationNumber(domain.getBusinessRegistrationNumber())
                .taxNumber(domain.getTaxNumber())
                .capitalShare(domain.getCapitalShare())
                .registrationDate(domain.getRegistrationDate())
                .ceoName(domain.getCeoName())
                .yearFounded(domain.getYearFounded())
                .keywords(domain.getKeywords())
                .status(domain.getStatus())
                .build();
    }
}

