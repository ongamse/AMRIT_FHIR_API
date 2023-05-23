package com.wipro.fhir.r4.service.resource_model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.FamilyMemberHistory;
import org.hl7.fhir.r4.model.FamilyMemberHistory.FamilyHistoryStatus;
import org.hl7.fhir.r4.model.FamilyMemberHistory.FamilyMemberHistoryConditionComponent;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.fhir.r4.data.request_handler.ResourceRequestHandler;
import com.wipro.fhir.r4.data.resource_model.FamilyMemberHistoryDataModel;
import com.wipro.fhir.r4.repo.common.PatientEligibleForResourceCreationRepo;
import com.wipro.fhir.r4.service.common.CommonServiceImpl;

/***
 * 
 * @author NE298657
 *
 */

@Service
public class FamilyMemberHistoryResource {

	@Autowired
	private CommonServiceImpl commonServiceImpl;

	@Autowired
	private PatientEligibleForResourceCreationRepo patientEligibleForResourceCreationRepo;
	@Autowired
	private FamilyMemberHistoryDataModel familyMemberHistoryDataModel;

	private FamilyMemberHistory familyMemberHistory;
	private String UUID;

	public FamilyMemberHistory getFamilyMemberHistory(Patient patient, ResourceRequestHandler resourceRequestHandler) {

		List<Object[]> rsObjList = patientEligibleForResourceCreationRepo.callFamilyMemberHistorySP(
				resourceRequestHandler.getBeneficiaryRegID(), resourceRequestHandler.getVisitCode());

		List<FamilyMemberHistoryDataModel> familyMemberHistoryList = familyMemberHistoryDataModel
				.getFamilyMemberHistoryList(rsObjList);

		return generateFamilyMemberHistory(patient, familyMemberHistoryList);
	}

	private FamilyMemberHistory generateFamilyMemberHistory(Patient patient,
			List<FamilyMemberHistoryDataModel> familyMemberHistoryList) {
		familyMemberHistory = new FamilyMemberHistory();
		UUID = commonServiceImpl.getUUID();
		familyMemberHistory.setId(UUID);

		familyMemberHistory.setStatus(FamilyHistoryStatus.HEALTHUNKNOWN);

		Map<String, String> diseaseMap;
		List<Map<String, String>> diseaseList;
		Map<String, List<Map<String, String>>> relationWiseDiseaseMap = new HashMap();

		String[] familyMemberArr;
		for (FamilyMemberHistoryDataModel obj : familyMemberHistoryList) {
			if (obj != null && obj.getFamilyMembers() != null && obj.getFamilyMembers().length() > 0) {
				familyMemberArr = obj.getFamilyMembers().split(",");
				if (familyMemberArr != null && familyMemberArr.length > 0) {
					for (String member : familyMemberArr) {
						diseaseMap = new HashMap<>();
						diseaseMap.put("sctCode", obj.getSctcode());
						diseaseMap.put("sctTerm", obj.getSctTerm());
						if (relationWiseDiseaseMap.containsKey(member.trim())) {
							diseaseList = relationWiseDiseaseMap.get(member.trim());
						} else {
							diseaseList = new ArrayList<>();

						}
						diseaseList.add(diseaseMap);
						relationWiseDiseaseMap.put(member.trim(), diseaseList);
					}
				}
			}

		}

		// codeble concept for relationship type
		CodeableConcept ccRelation = new CodeableConcept();
		Coding cRelation;
		List<Coding> cRelationList = new ArrayList<Coding>();

		// condition - backbone
		CodeableConcept ccDisease;
		Coding cDisease;
		List<Coding> cDiseaseList;
		FamilyMemberHistoryConditionComponent fmhcc;
		List<FamilyMemberHistoryConditionComponent> fmhccList = new ArrayList<>();

		// set each unique family members in coding list
		for (Map.Entry<String, List<Map<String, String>>> entry : relationWiseDiseaseMap.entrySet()) {
			// relation
			cRelation = new Coding();
			cRelation.setCode(entry.getKey());
			cRelation.setDisplay(entry.getKey());
			cRelationList.add(cRelation);

			// disease,family member disease history
			fmhcc = new FamilyMemberHistoryConditionComponent();
			ccDisease = new CodeableConcept();
			cDiseaseList = new ArrayList<>();
			List<Map<String, String>> dList = entry.getValue();
			if (dList != null && dList.size() > 0) {
				for (Map<String, String> dMap : dList) {

					cDisease = new Coding();
					cDisease.setCode(dMap.get("sctCode"));
					cDisease.setDisplay(dMap.get("sctTerm"));
					cDisease.setSystem("http://snomed.info/sct");

					cDiseaseList.add(cDisease);
				}
				ccDisease.setCoding(cDiseaseList);
			}

			fmhcc.setCode(ccDisease);
			fmhccList.add(fmhcc);

		}

		ccRelation.setCoding(cRelationList);
		familyMemberHistory.setRelationship(ccRelation);

		familyMemberHistory.setCondition(fmhccList);

		// referance - patient
		familyMemberHistory.setPatient(new Reference(patient.getIdElement().getValue()));

		return familyMemberHistory;
	}
}
