package com.codercampus.Assignment11.web;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codercampus.Assignment11.domain.Transaction;
import com.codercampus.Assignment11.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/transactions")
	public String getAllTransactions(ModelMap model) {
		List<Transaction> transactions = transactionService.findAll();
		
		model.put("transactions", transactions.stream()
											  .sorted(Comparator.comparing(Transaction :: getDate))
											  .collect(Collectors.toList()));
		return "transactions";
	}
	
	@GetMapping("/transactions/{transactionId}")
	public String getTransaction(@PathVariable Long transactionId, ModelMap model) {
		Transaction foundTransaction = transactionService.findById(transactionId);
		String transactionType = null;
		if(foundTransaction.getType().equals("D")) {
			transactionType = "Debit";
		} else if (foundTransaction.getType().equals("C")) {
			transactionType = "Credit";
		}
		model.put("foundTransaction", foundTransaction);
		model.put("transactionType", transactionType);
		return "transactionDetails";
	}
}
