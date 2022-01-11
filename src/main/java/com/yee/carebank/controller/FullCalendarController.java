package com.yee.carebank.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yee.carebank.model.biz.MoodBiz;
import com.yee.carebank.model.biz.PillsBiz;
import com.yee.carebank.model.biz.ScheduleBiz;
import com.yee.carebank.model.dto.MoodDto;
import com.yee.carebank.model.dto.PillsDto;
import com.yee.carebank.model.dto.ScheduleDto;
import com.yee.carebank.model.dto.UserDto;

@Controller
public class FullCalendarController {

	@Autowired
	private ScheduleBiz sBiz;

	@Autowired
	private MoodBiz mBiz;

	@Autowired
	private PillsBiz pBiz;

	private Logger logger = LoggerFactory.getLogger(FullCalendarController.class);

	@RequestMapping("/diary.do")
	public String diary(Model model, HttpSession session) {
		logger.info("mypage_diary");

		UserDto loginUser = (UserDto) session.getAttribute("login_info");
		/*
		 * if(loginUser != null) List<Dto> list = sBiz.selectList(parameter값으로 loginUser
		 * getUser_no) else {
		 * 
		 * List<Dto> list = sBiz.selectList(1011); }
		 * 
		 * model.addattribute("list", list);
		 */

		List<ScheduleDto> list = null;
		if (loginUser != null) {
			list = sBiz.selectList(loginUser.getUser_no());
		} else {
			list = sBiz.selectList(1011);
			// return "main";
		}
		
		List<PillsDto> Pills = null;
		if(loginUser != null) {
			Pills = pBiz.selectList(loginUser.getUser_no());
		}else {
			Pills = pBiz.selectList(1011);
		}
		
		model.addAttribute("dto", list); // list
		model.addAttribute("pills", Pills);
		return "mypage_diary";
	}

	@RequestMapping("/schedulePopup.do")
	public String schedule() {
		logger.info("mypage_schedulePopup");
		return "schedulePopup";
	}

	@RequestMapping("/moodPopup.do")
	public String mood() {
		logger.info("mood");
		return "moodPopup";
	}

	@RequestMapping("/schedule.do")
	public String update(Model model, int hospital_no) {
		logger.info("schedule");

		System.out.println(hospital_no);
		model.addAttribute("dto", sBiz.selectOne(hospital_no));
		return "updatePopup";
	}

	@ResponseBody
	@RequestMapping(value = "/scheduleupdate.do", method = RequestMethod.POST)
	public String updateSchedule(HttpSession session, @RequestBody ScheduleDto dto) {
		logger.info("update schedule");

		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		int res = 0;
		if(loginUser.getUser_no() == 1011) {
			System.out.println(loginUser.getUser_no());
			res = sBiz.update(dto);
		}else {
		 }

		String st = Integer.toString(res);

		return st;

	}

	@ResponseBody
	@RequestMapping(value = "/addschedule.do", method = RequestMethod.POST)
	public Map<Object, Object> insert(HttpSession session, @RequestBody ScheduleDto dto) {
		logger.info("insert");

		UserDto loginUser = (UserDto) session.getAttribute("login_info");
		/*
		 * if(loginUser != null) List<Dto> list = sBiz.selectList(parameter값으로 loginUser
		 * getUser_no) else {
		 * 
		 * List<Dto> list = sBiz.selectList(1011); }
		 * 
		 * model.addattribute("list", list);
		 */

		if (loginUser != null) {
			dto.setUser_no(loginUser.getUser_no());
		} else {
			dto.setUser_no(1011);
		}

		Map<Object, Object> map = new HashMap<Object, Object>();
		int res = sBiz.insert(dto);

		map.put("res", res);

		// session.setAttribute("insert", dto.getUser_no());
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/moodschedule.do", method = RequestMethod.POST)
	public Map<Object, Object> insert(@RequestBody MoodDto dto, HttpSession session) {
		logger.info("MOOD INSERT");
		Map<Object, Object> map = new HashMap<Object, Object>();

		int res = mBiz.insert(dto);

		map.put("res", res);

		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/scheduledelete.do", method = RequestMethod.POST)
	public String delete(@RequestBody ScheduleDto dto) {
		logger.info("schedule delete");

		int res = sBiz.delete(dto);

		String hosp = Integer.toString(res);

		return hosp;
	}

	@RequestMapping("/pillsPopup.do")
	public String pills() {
		logger.info("pills schedule");
		return "pillsPopup";
	}

	@ResponseBody
	@RequestMapping(value = "/pillschedule.do", method = RequestMethod.POST)
	public String pillsinsert(@RequestBody PillsDto dto, HttpSession session) {
		logger.info("insert pills");

		UserDto loginUser = (UserDto) session.getAttribute("login_info");
		
		if(loginUser != null) {
			dto.setUser_no(loginUser.getUser_no());
		}else {
			dto.setUser_no(1011);
		}
		
		int res = pBiz.insert(dto);

		String pills = Integer.toString(res);

		return pills;
	}
	
	@RequestMapping("/pillsDelete.do")
	public String pillsdelete(int pills_no) {
		logger.info("PillsInfo delete");
		
		int res = pBiz.delete(pills_no);
		
		if(res>0) {
			return "redirect:diary.do";
		}else {
			return "redirect:diary.do";
		}
	}
}
