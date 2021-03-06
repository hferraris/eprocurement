package com.eprocurement.purchaserequest;

import com.eprocurement.department.DepartmentRepository;
import com.eprocurement.user.User;
import com.eprocurement.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pr")
public class PurchaseRequestController {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private PurchaseRequestService purchaseRequestService;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/new")
	public String getNewPurchaseRequestForm(Model model) {
		model.addAttribute("departments", departmentRepository.findAll());
		model.addAttribute("purchaseRequest", new PurchaseRequest());
		return "purhcaseRequest";
	}
	
	@PostMapping("/save")
	public String savePurchaseRequest(@ModelAttribute PurchaseRequest purchaseRequest, Model model) {
			purchaseRequestService.createNewPurchaseRequest(purchaseRequest);
			return "redirect:/pr/"+purchaseRequest.getPrNo()+"/details";
	}
	
	//display purchase requests
	@GetMapping("/all")
	public String showAllPurchaseRequests(Model model) {
		model.addAttribute("departments", departmentRepository.findAll());
		return "purchaseRequests";
	}
	
	@GetMapping
	public String showDefaultPurchaseRequestManagementPage() {
		return "redirect:/pr/all";
	}
	
	@GetMapping("/{pr}/details")
	public String selectPRItems(@PathVariable PurchaseRequest pr, Model model, Authentication authentication) {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		if(userDetails.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			model.addAttribute("departments", departmentRepository.findAll());
			return "prDetails";
				
		}else {
			User user = userRepository.findByUsername(userDetails.getUsername());
			if(pr.getDepartment() == user.getDepartment()) {
				model.addAttribute("departments", departmentRepository.findAll());
				return "prDetails";
			}else {
				
				throw new AccessDeniedException("Access Denied");
			}		
		}	
	}
	
}
