/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.wipro.fhir.r4.config.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.fhir.r4.service.common.CommonService;

@Service
@Transactional
public class Scheduler_Job_Patient_Profile_NDHM implements Job {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private CommonService commonService;

	// run the schedular for patient profile(creation, process and mongo save)
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Started job for patient profile creation " + arg0.getClass().getName());

		try {
			// process resource creation
			commonService.processPatientProfileCreationAMRIT();

		} catch (Exception e) {
			
			logger.error("Unexpected error:" , e);
		}
		logger.info("Completed job for patient profile creation " + arg0.getClass().getName());
	}

}
