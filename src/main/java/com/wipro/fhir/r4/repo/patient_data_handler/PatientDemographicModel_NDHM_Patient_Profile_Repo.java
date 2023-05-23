package com.wipro.fhir.r4.repo.patient_data_handler;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.wipro.fhir.r4.data.patient_data_handler.PatientDemographicModel_NDHM_Patient_Profile;

@Repository
@RestResource(exported = false)
public interface PatientDemographicModel_NDHM_Patient_Profile_Repo
		extends MongoRepository<PatientDemographicModel_NDHM_Patient_Profile, String> {

	List<PatientDemographicModel_NDHM_Patient_Profile> findByHealthId(String healthID);

	List<PatientDemographicModel_NDHM_Patient_Profile> findByHealthIdNumber(String healthIdNumber);

	List<PatientDemographicModel_NDHM_Patient_Profile> findByAmritId(String amritId);

	List<PatientDemographicModel_NDHM_Patient_Profile> findByExternalId(String externalId);

	Page<PatientDemographicModel_NDHM_Patient_Profile> findByHealthId(@Param("healthID") String healthID,
			Pageable pageable);

	Page<PatientDemographicModel_NDHM_Patient_Profile> findByHealthIdNumber(String healthIdNumber, Pageable pageable);

	Page<PatientDemographicModel_NDHM_Patient_Profile> findByAmritId(String amritId, Pageable pageable);

	Page<PatientDemographicModel_NDHM_Patient_Profile> findByExternalId(String externalId, Pageable pageable);

//	Page<PatientDemographicModel_NDHM_Patient_Profile> find(org.springframework.data.mongodb.core.query.Query query,
//			Page<PatientDemographicModel_NDHM_Patient_Profile> p);

	Page<PatientDemographicModel_NDHM_Patient_Profile> findAllByOrderByIdDesc(Pageable pr);

	Page<PatientDemographicModel_NDHM_Patient_Profile> findAllByOrderByTimestampDesc(Pageable pr);

	Page<PatientDemographicModel_NDHM_Patient_Profile> findByExternalIdOrderByTimestampDesc(Pageable pr);

	/// 25-01-2022
	// search patient phone no
	@Query("{ 'profile.patient.identifiers.MOBILE': ?0 }")
	Page<PatientDemographicModel_NDHM_Patient_Profile> searchPatientByPhoneNo(String phoneNo, Pageable pr);

	@Query("{ 'profile.patient.address.state': ?0 }")
	Page<PatientDemographicModel_NDHM_Patient_Profile> searchPatientByAddressState(String state, Pageable pr);

	@Query("{ 'profile.patient.address.district': ?0 }")
	Page<PatientDemographicModel_NDHM_Patient_Profile> searchPatientByAddressDistrict(String district, Pageable pr);

	@Query("{ 'profile.patient.address.village': ?0 }")
	Page<PatientDemographicModel_NDHM_Patient_Profile> searchPatientByAddressVillage(String village, Pageable pr);

}
