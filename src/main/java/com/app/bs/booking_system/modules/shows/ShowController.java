package com.app.bs.booking_system.modules.shows;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.shows.dto.CreateShowDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/show")
public class ShowController {
  private final ShowService showService;

  public ShowController(ShowService showService) {
    this.showService = showService;
  }

  @PostMapping("")
  public Show createShow(@RequestBody CreateShowDTO showDTO) {
    return showService.createShow(showDTO);
  }
  
  @GetMapping("")
  public List<Show> getMethodName() {
    return showService.getShows();
  }
  

}
