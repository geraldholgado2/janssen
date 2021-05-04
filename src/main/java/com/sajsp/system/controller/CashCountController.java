package com.sajsp.system.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sajsp.system.exception.CashCountException;
import com.sajsp.system.request.CashCountRequest;
import com.sajsp.system.service.CashCountService;

import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "controller")
@RestController
@RequestMapping(value = "/api/system/")
@CrossOrigin
public class CashCountController {

	@Autowired
	private CashCountService cashCountService;

	@PostMapping("/cash-counts")
	public ResponseEntity<?> addCashCount(@Valid @RequestBody CashCountRequest request) throws CashCountException {
		log.info(String.format("Add cash count with request: %s", request.toString()));
		return new ResponseEntity<>(cashCountService.addCashCount(request), HttpStatus.OK);
	}

	@GetMapping("/cash-counts/{date}")
	public ResponseEntity<?> getCashCountsByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
			@RequestParam Integer page, @RequestParam Integer size) throws CashCountException {
		log.info(String.format("GET cash counts with date : %s", date.toString()));
		return new ResponseEntity<>(cashCountService.getCashCount(date, page, size), HttpStatus.OK);
	}

	@GetMapping("/daily-cash-counts/{date}")
	public ResponseEntity<?> getDailyCashCountsByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
			@RequestParam Integer page, @RequestParam Integer size) throws CashCountException {
		log.info(String.format("GET cash counts with date : %s", date.toString()));
		return new ResponseEntity<>(cashCountService.getDailyCashCount(date, page, size), HttpStatus.OK);
	}
}
