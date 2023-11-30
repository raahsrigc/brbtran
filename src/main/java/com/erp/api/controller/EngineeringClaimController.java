package com.erp.api.controller;

	import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.erp.api.dto.ClaimRegistrationDto;
import com.erp.api.dto.InsuredCustomerDTO;
import com.erp.api.dto.PaginationDTO;
import com.erp.api.dto.PolicyHeaderDetailsDto;
import com.erp.api.dto.ResponseMessage;
import com.erp.api.serviceImpl.EngineeringClaimService;

	@RestController
	public class EngineeringClaimController {

		@Autowired
		EngineeringClaimService engineeringClaimService;
		


		final Logger log = LoggerFactory.getLogger(this.getClass());

	
		






		
	///////////////
		
		
		@PostMapping(value = "v1/claimRegistration")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> claimRegistration(@RequestHeader("token") String sessionId, @RequestBody ClaimRegistrationDto claimRegistrationDto) {
			log.info("Request landed for claimRegistration at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.registerClaim(sessionId,claimRegistrationDto, responseMessage1);
			log.info("Response is sent from claimRegistration at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@GetMapping(value = "v1/GetClaimDetailsById")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> GetClaimDetailsById(@RequestHeader("token") String sessionId,@RequestHeader("ClaimId") String claimId) {
			log.info("Request landed for GetClaimDetailsById at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.GetClaimById(sessionId, claimId, responseMessage1);
			log.info("Response is sent from GetClaimDetailsById at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getAllClaims")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllClaims(@RequestHeader("token") String sessionId, @RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllClaims at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			String lob="1";
			String businessType="2";
			responseMessage1 = engineeringClaimService.getAllClaims(sessionId, lob, businessType, paginationDTO, responseMessage1);
			log.info("Response is sent from getAllClaims at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getAllClaimsForDashboard")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllClaimsForDashboard(@RequestHeader("token") String sessionId, @RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllClaimsForDashboard at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			String lob="1";
			String businessType="2";
			responseMessage1 = engineeringClaimService.getAllClaims(sessionId, lob, businessType, paginationDTO, responseMessage1);
			log.info("Response is sent from getAllClaimsForDashboard at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@GetMapping(value = "v1/getCauseOfLoss")
		@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
		public ResponseEntity<ResponseMessage> getCauseOfLoss(@RequestHeader("token") String sessionId,@RequestHeader("LOB") String lob) {
			log.info("Request landed for getCauseOfLoss at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getCauseOfLoss(sessionId, lob, responseMessage1);
			log.info("Response is sent from getCauseOfLoss at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@GetMapping(value = "v1/getNatureOfLoss")
		@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
		public ResponseEntity<ResponseMessage> getNatureOfLoss(@RequestHeader("token") String sessionId,@RequestHeader("LOB") String lob) {
			log.info("Request landed for getNatureOfLoss at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getNatureOfLoss(sessionId, lob, responseMessage1);
			log.info("Response is sent from getNatureOfLoss at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		
		
		@PostMapping(value = "v1/getVehicleMake")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getVehicleMake(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getVehicleMake at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getMastercodePagination(sessionId, "Vehicle MAke", paginationDTO, responseMessage1);
			log.info("Response is sent from getVehicleMake at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@GetMapping(value = "v1/getVehicleModel")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getVehicleModel(@RequestHeader("token") String sessionId,@RequestHeader("MakeCode") String makeCode) {
			log.info("Request landed for getVehicleModel at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getModel(sessionId, makeCode, responseMessage1);
			log.info("Response is sent from getVehicleModel at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@GetMapping(value = "v1/getVehicleType")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getVehicleType(@RequestHeader("token") String sessionId) {
			log.info("Request landed for getVehicleType at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getVehicleType(sessionId, "Private Motor", responseMessage1);
			log.info("Response is sent from getVehicleType at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		
		@PostMapping(value = "v1/getWalletSOA")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getWalletSOA(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getWalletSOA at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getWalletSOA(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getWalletSOA at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getWalletSOAForDashboard")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getWalletSOAForDashboard(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getWalletSOAForDashboard at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getWalletSOA(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getWalletSOAForDashboard at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getWalletSOAForProducts")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getWalletSOAForProducts(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getWalletSOAForProducts at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getWalletSOAforProduct(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getWalletSOAForProducts at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/saveRenewalEndorsementDetails")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> saveRenewalEndorsementDetails(@RequestHeader("token") String sessionId,@RequestHeader("PolicyId") String policyId, @RequestBody PolicyHeaderDetailsDto policyHeaderDetailsDto) {
			log.info("Request landed for updateRenewalEndorsement at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.updateRenewalEndorsement(sessionId,policyId, policyHeaderDetailsDto, responseMessage1);
			log.info("Response is sent from updateRenewalEndorsement at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@GetMapping(value = "v1/getEndorsementDetails")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getEndorsementDetails(@RequestHeader("token") String sessionId,@RequestHeader("EndorsementNumber") String endorsementNumber,@RequestHeader("PolicyNumber") String policyNumber) {
			log.info("Request landed for getEndorsementDetails at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getEndorsementDetails(sessionId, endorsementNumber,policyNumber, responseMessage1);
			log.info("Response is sent from getEndorsementDetails at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping("v1/updateKYCDetails")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> updateInsuredCustomerDetails(@RequestBody InsuredCustomerDTO insuredCustomerDTO,@RequestHeader("token") String sessionId) {
			log.info("Request landed for updateInsuredCustomerDetails at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			responseMessage1 = engineeringClaimService.updateInsuredCustomer(insuredCustomerDTO, sessionId, responseMessage1);
			log.info("Response is sent from updateInsuredCustomerDetails at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		
		@PostMapping(value = "v1/getAllApprovedPolicies")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllApprovedPolicies(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllApprovedPolicies at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getAllApprovedPolicies(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getAllApprovedPolicies at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getAllSettledClaims")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllSettledClaims(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllSettledClaims at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getAllSettledClaims(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getAllSettledClaims at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getAllQuotations")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllQuotations(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllQuotations at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getAllQuotations(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getAllQuotations at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getAllApprovedClaims")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllApprovedClaims(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllApprovedClaims at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getAllApprovedClaims(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getAllApprovedClaims at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@PostMapping(value = "v1/getAllPendingClaims")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllPendingClaims(@RequestHeader("token") String sessionId,@RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllPendingClaims at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getAllPendingClaims(sessionId,paginationDTO, responseMessage1);
			log.info("Response is sent from getAllPendingClaims at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		@GetMapping(value = "v1/getClaimsSummary")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getClaimsSummary(@RequestHeader("token") String sessionId,@RequestHeader("LobId") String lobId) {
			log.info("Request landed for getClaimsSummary at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			
			responseMessage1 = engineeringClaimService.getClaimsSummary(sessionId,lobId, responseMessage1);
			log.info("Response is sent from getClaimsSummary at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
		
		
		
		@PostMapping(value = "v1/getAllPersonalAccidentClaims")
		@CrossOrigin(origins = "*", maxAge = 3600)
		public ResponseEntity<ResponseMessage> getAllPersonalAccidentClaims(@RequestHeader("token") String sessionId, @RequestBody PaginationDTO paginationDTO) {
			log.info("Request landed for getAllPersonalAccidentClaims at Txn Manager");
			ResponseMessage responseMessage1 = new ResponseMessage();
			String lob="6";
			String businessType="2";
			responseMessage1 = engineeringClaimService.getAllClaims(sessionId, lob, businessType, paginationDTO, responseMessage1);
			log.info("Response is sent from getAllPersonalAccidentClaims at Txn Manager");
			return new ResponseEntity<>(responseMessage1, HttpStatus.OK);

		}
		
	}