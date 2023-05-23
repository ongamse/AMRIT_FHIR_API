package com.wipro.fhir.r4.controller.generate_resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.fhir.r4.data.request_handler.ResourceRequestHandler;
import com.wipro.fhir.r4.service.resource_gateway.DiagnosticReportRecord;
import com.wipro.fhir.r4.service.resource_gateway.OPConsultRecordBundle;
import com.wipro.fhir.r4.service.resource_gateway.PrescriptionRecordBundle;
import com.wipro.fhir.r4.utils.exception.FHIRException;
import com.wipro.fhir.r4.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author NE298657
 * 
 *         Implement the check if a bundle resource is already there for a
 *         care-context, fetch from Mongo instead creating again ***
 *
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/get/resource", headers = "Authorization")
public class ResourceRequestGateway {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private OPConsultRecordBundle opConsultRecordBundle;
	@Autowired
	private PrescriptionRecordBundle prescriptionRecordBundle;
	@Autowired
	private DiagnosticReportRecord diagnosticReportRecord;

	/***
	 * 
	 * @param patientResourceRequest
	 * @param Authorization
	 * @return (OPConsultRecord - Bundle){ Patient || Encounter || Organization ||
	 *         Condition || Observation || AllergyIntolerance || FamilyMemberHistory
	 *         || ServiceRequest || MedicationStatement || Appointment ||
	 *         DocumentReference}
	 * 
	 */
	@CrossOrigin
	@ApiOperation(value = "get OP Consult Record bundle", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/OPConsultRecord" }, method = { RequestMethod.POST })
	public String getPatientResource(@RequestBody ResourceRequestHandler patientResourceRequest,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		try {
			String s = opConsultRecordBundle.getOPConsultRecordBundle(patientResourceRequest, null);

			response.setResponse(s);
		} catch (FHIRException e) {
			logger.error("error in creating OP Consult Record bundle : " + e.getMessage());
			response.setError(5000, "error in creating OP Consult Record bundle : " + e.getMessage());
		}
		return response.toString();
	}

	/***
	 * 
	 * @param patientResourceRequest
	 * @param Authorization
	 * @return (DiagnosticReportRecord - Bundle){ Patient || Encounter ||
	 *         Practitioner || Organization || DiagnosticReportLab ||
	 *         DocumentReference}
	 * 
	 */
	@CrossOrigin
	@ApiOperation(value = "get Diagnostic Report Record bundle", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/DiagnosticReportRecord" }, method = { RequestMethod.POST })
	public String getDiagnosticReportRecord(@RequestBody ResourceRequestHandler patientResourceRequest,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		try {
			String s = diagnosticReportRecord.getDiagnosticReportRecordBundle(patientResourceRequest, null);

			response.setResponse(s);
		} catch (FHIRException e) {
			logger.error("error in creating Diagnostic Report Record bundle : " + e.getMessage());
			response.setError(5000, "error in creating Diagnostic Report Record bundle : " + e.getMessage());
		}
		return response.toString();
	}

	/***
	 * 
	 * @param patientResourceRequest
	 * @param Authorization
	 * @return (PrescriptionRecord - Bundle){ Patient || Encounter || Practitioner
	 *         || Organization || MedicationRequest || Binary}
	 * 
	 */
	@CrossOrigin
	@ApiOperation(value = "get Prescription Record", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/PrescriptionRecord" }, method = { RequestMethod.POST })
	public String getPrescriptionRecord(@RequestBody ResourceRequestHandler patientResourceRequest,
			@RequestHeader(value = "Authorization") String Authorization) {

		OutputResponse response = new OutputResponse();
		try {
			String s = prescriptionRecordBundle.getPrescriptionRecordBundle(patientResourceRequest, null);

			response.setResponse(s);
		} catch (FHIRException e) {
			logger.error("error in creating Prescription Record bundle : " + e.getMessage());
			response.setError(5000, "error in creating Prescription Record bundle : " + e.getMessage());
		}
		return response.toString();
	}

}
