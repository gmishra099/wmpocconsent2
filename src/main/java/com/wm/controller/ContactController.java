package com.wm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wm.model.Consent;
import com.wm.model.Contact;
import com.wm.model.Individual;
import com.wm.service.ConsentService;
import com.wm.service.ContactService;
import com.wm.service.IndividualService;
import com.wm.wrapper.WrapperConsent;
import com.wm.wrapper.WrapperIndividual;

@RestController
@RequestMapping({ "/api" })
public class ContactController {
	
	@Autowired
	ContactService contactService;
	@Autowired
	IndividualService individualService;
	
	@Autowired
	ConsentService consentService;
	
		@RequestMapping(value = "/test", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
		public String home(Model model) {
			System.out.println("test");
			return "testhome123";
		}
		
		@RequestMapping(value = "/test1", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
		public List<Contact> home1() {
			System.out.println("test");
			contactService.getAllContacts();
			return contactService.getAllContacts();
		}
		
		@RequestMapping(value = "/test2", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
		public List<Individual> home2() {
			 
			//contactService.getAllContacts();
			return individualService.getAllIndividuals();
		}
		
		@RequestMapping(value = "/test3", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
		public List<WrapperIndividual> home3() {
			List<WrapperIndividual> WrapperIndividual = new ArrayList<WrapperIndividual>();
			
			List<Individual> indiviList = individualService.getAllIndividuals();
			List<Contact> conList = contactService.getAllContacts();
			
			for( Contact con:conList) {
				for(Individual indivi:indiviList) {
					if( con.getCusId() != null) {
						
						if(con.getCusId().equals("10001")) {
							WrapperIndividual obj = new WrapperIndividual();
							obj.setCusId("10001");;
							obj.setHasoptedouttracking(indivi.getHasoptedouttracking());
							obj.setName(indivi.getName());
							obj.setShouldforget(indivi.getShouldforget());
							WrapperIndividual.add(obj);
						}
							
						
					}


				}
				
			}
			
			
			return WrapperIndividual;
		}
		
		@RequestMapping(value = "/test4", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
		public Contact home4() {
			long Id = 21;
			Optional<Contact> con=contactService.getContactById(Id);
			Contact conobj=con.get();
			return conobj;
		}
		
		@RequestMapping(value = "/test5", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
		public WrapperIndividual home5() {
			Contact con=contactService.findByCustId("10001");
			String indSFID= con.getIndividualid();
			Individual ind=individualService.findByindSFID(indSFID);
			WrapperIndividual obj = new WrapperIndividual();
			obj.setCusId(con.getCusId());
			obj.setHasoptedouttracking(ind.getHasoptedouttracking());
			obj.setName(ind.getName());
			obj.setShouldforget(ind.getShouldforget());
			String indSfid = ind.getIndSFID();
			List<Consent> consents=consentService.findByconsentGiverId(indSfid);
			List<WrapperConsent> wrapperConsentList = new ArrayList<WrapperConsent>();
			for(Consent consent: consents) {
				WrapperConsent wrapperConsentObj = new WrapperConsent();
				wrapperConsentObj.setContactPoint(consent.getContactPointId());
				wrapperConsentObj.setConsentState(consent.getConsent__c());;
				wrapperConsentObj.setConsentName(consent.getName());
				wrapperConsentObj.setCommSubscriptionFormula(consent.getComm_sub__c());
				wrapperConsentObj.setCommSubscriptionChannelType(consent.getCommSubscriptionChannelTypeId());
				wrapperConsentList.add(wrapperConsentObj);
			}
			obj.setWrapperConsentList(wrapperConsentList);
			return obj;
		}

		@RequestMapping(value = "/test6", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
		public List<Consent> home6() {
			long Id = 21;
			List<Consent> consentList=consentService.findByconsentGiverId("0PK2w000000bmCTGAY");
			return consentList;
		}
		
	
}
