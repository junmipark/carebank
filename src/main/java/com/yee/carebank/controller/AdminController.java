package com.yee.carebank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yee.carebank.model.biz.AdminBiz;
import com.yee.carebank.model.biz.MealBiz;
import com.yee.carebank.model.dto.FoodDto;
import com.yee.carebank.model.dto.FoodsDto;
import com.yee.carebank.model.dto.MealDto;
import com.yee.carebank.model.dto.UserDto;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	AdminBiz biz;

	@Autowired
	MealBiz mBiz;

	/*
	 * 관리자 - 식단 페이지 관리자 메인은 가장 상단에 있는 식단 페이지를 보여줌
	 */

	@RequestMapping("admin/main.do")
	public String main(HttpSession session, Model model) {
		logger.info("ADMIN MAIN");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		List<MealDto> res = biz.selectMList(1);

		try {
			String userType = loginUser.getUser_type();
			if (!(userType).equals("ADMIN")) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}
		} catch (Exception e) {
			logger.error("LOGIN NOT FOUND");
			return "redirect: ../main.do";
		}

		model.addAttribute("res", res);
		model.addAttribute("cnt", biz.getMTotalCnt());
		model.addAttribute("page", 1);
		model.addAttribute("category", biz.selectCList());

		return "admin/meal/mlist";
	}

	@RequestMapping("admin/meal.do")
	public String selectList(HttpSession session, Model model, @RequestParam("page") int page) {
		logger.info("GET LIST [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		List<MealDto> res = null;

		try {
			String userType = loginUser.getUser_type();
			if (!(userType).equals("ADMIN")) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}
		} catch (Exception e) {
			logger.error("LOGIN NOT FOUND");
			return "redirect: ../main.do";
		}

		res = biz.selectMList(page);

		model.addAttribute("res", res);
		model.addAttribute("cnt", biz.getMTotalCnt());
		model.addAttribute("page", page);
		model.addAttribute("category", biz.selectCList());

		return "admin/meal/mlist";
	}

	@RequestMapping("admin/minfo.do")
	public String selectMeal(HttpSession session, Model model, @RequestParam("id") int meal_id) {
		logger.info("GET DETAILS [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType).equals("ADMIN")) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}
		} catch (Exception e) {
			logger.error("LOGIN NOT FOUND");
			return "redirect: ../main.do";
		}

		model.addAttribute("meal", mBiz.selectOne(meal_id));
		model.addAttribute("food", mBiz.selectIngredient(meal_id));

		return "admin/meal/minfo";
	}

	@RequestMapping("admin/mwrite.do")
	public String writeMeal(HttpSession session, Model model) {
		logger.info("WRITE INFORMATION [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		model.addAttribute("category", biz.selectCList());
		return "admin/meal/mwrite";
	}

	@RequestMapping("admin/minsert.do")
	public String insertM(HttpSession session, Model model, MealDto meal, @ModelAttribute FoodsDto foods) {
		logger.info("INSERT DATA [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		int res = biz.insertM(meal, foods.getFoods());

		if (res > 0) {
			model.addAttribute("msg", "데이터가 등록되었습니다.");
			model.addAttribute("url", "meal.do");
		} else {
			model.addAttribute("msg", "데이터가 등록되지 않았습니다.");
			model.addAttribute("url", "meal.do");
		}

		return "admin/redirect";
	}

	@RequestMapping("admin/mmodi.do")
	public String updateMeal(HttpSession session, Model model, @RequestParam("id") int meal_id) {
		logger.info("UPDATE PAGE [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			} else {
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		model.addAttribute("meal", mBiz.selectOne(meal_id));
		model.addAttribute("food", mBiz.selectIngredient(meal_id));
		model.addAttribute("category", biz.selectCList());

		return "admin/meal/mmodify";

	}

	@RequestMapping("admin/mmodify.do")
	public String updateM(HttpSession session, Model model, MealDto meal, @ModelAttribute FoodsDto foods) {
		logger.info("MODIFY DATA [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		int res = biz.updateM(meal, foods.getFoods());

		if (res > 0) {
			model.addAttribute("msg", "데이터가 수정되었습니다.");
			model.addAttribute("url", "meal.do");
		} else {
			model.addAttribute("msg", "데이터가 수정되지 않았습니다.");
			model.addAttribute("url", "meal.do");
		}

		return "admin/redirect";
	}

	@RequestMapping("admin/mdel.do")
	public String deleteMeal(HttpSession session, Model model, @RequestParam("id") int meal_id) {
		logger.info("DELETE DATA [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			} else {
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		int res = biz.deleteMeal(meal_id);

		if (res > 0) {
			model.addAttribute("msg", "데이터가 삭제되었습니다.");
			model.addAttribute("url", "meal.do");
		} else {
			model.addAttribute("msg", "데이터가 삭제되지 않았습니다.");
			model.addAttribute("url", "meal.do");
		}

		return "admin/redirect";
	}

	@RequestMapping("admin/msearch.do")
	public String searchMeal(HttpSession session, Model model, @RequestParam String search,
			@RequestParam String keyword, @RequestParam int page) {
		logger.info("SEARCH DATA [MEAL]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}

			model.addAttribute("res", biz.search(search, keyword, page));
			model.addAttribute("cnt", biz.getCount(search, keyword));
			model.addAttribute("search", search);
			model.addAttribute("keyword", keyword);
			model.addAttribute("page", page);
			model.addAttribute("category", biz.selectCList());
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		return "admin/meal/msearch";
	}

	/*
	 * 식단 - 영양소 페이지
	 */
	@RequestMapping("admin/food.do")
	public String selectFood(HttpSession session, Model model, @RequestParam int page) {
		logger.info("GET LIST [FOOD]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");
		FoodsDto foods = new FoodsDto();

		try {
			String userType = loginUser.getUser_type();
			if (!(userType).equals("ADMIN")) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}
		} catch (Exception e) {
			logger.error("LOGIN NOT FOUND");
			return "redirect: ../main.do";
		}

		foods.setFoods(biz.selectFList(page));
		model.addAttribute("res", foods.getFoods());
		model.addAttribute("cnt", biz.getFTotalCount());
		model.addAttribute("page", page);

		return "admin/food/flist";
	}

	@RequestMapping("admin/fdel.do")
	public String deleteFood(HttpSession session, Model model, @RequestParam("id") int food_id) {
		logger.info("DELETE DATA [FOOD]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			} else {
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		int res = biz.deleteFood(food_id);

		if (res > 0) {
			model.addAttribute("msg", "데이터가 삭제되었습니다.");
			model.addAttribute("url", "food.do");
		} else {
			model.addAttribute("msg", "데이터가 삭제되지 않았습니다.");
			model.addAttribute("url", "food.do");
		}

		return "admin/food/redirect";
	}

	@RequestMapping("admin/fwrite.do")
	public String writeFood(HttpSession session) {
		logger.info(" WRITE INFORMATION [FOOD]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			} else {
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		return "admin/food/fwrite";
	}

	@RequestMapping("admin/finsert.do")
	public String writeFood(HttpSession session, Model model, FoodDto food) {
		logger.info("WRITE INFORMATION [FOOD]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			} else {
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		int res = biz.insertF(food);

		if (res > 0) {
			model.addAttribute("msg", "데이터가 등록되었습니다.");
			model.addAttribute("url", "food.do");
		} else {
			model.addAttribute("msg", "데이터가 등록되지 않았습니다.");
			model.addAttribute("url", "food.do");
		}

		return "admin/food/redirect";
	}

	@RequestMapping("admin/fcheck.do")
	@ResponseBody
	public int checkFName(HttpSession session, String foodname) {
		logger.info(" AJAX REQUEST [FOOD]");

		int check = biz.checkFName(foodname);

		return check;
	}

	@RequestMapping("admin/fmodi.do")
	public String updateFood(HttpSession session, Model model, @RequestParam("id") int food_id) {
		logger.info("UPDATE PAGE [FOOD]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			} else {
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		model.addAttribute("food", biz.selectFood(food_id));

		return "admin/food/fmodi";

	}

	@RequestMapping("admin/fupdate.do")
	public String updateFood(HttpSession session, Model model, FoodDto food) {
		logger.info("UPDATE INFORMATION [FOOD]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			} else {
			}
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		int res = biz.updateF(food);

		if (res > 0) {
			model.addAttribute("msg", "데이터가 수정되었습니다.");
			model.addAttribute("url", "food.do");
		} else {
			model.addAttribute("msg", "데이터가 수정되지 않았습니다.");
			model.addAttribute("url", "food.do");
		}

		return "admin/redirect";
	}

	@RequestMapping("admin/fsearch.do")
	public String searchFood(HttpSession session, Model model, @RequestParam String keyword, @RequestParam int page) {
		logger.info(" SEARCH DATA [FOOD]");
		UserDto loginUser = (UserDto) session.getAttribute("login_info");

		try {
			String userType = loginUser.getUser_type();
			if (!(userType.equals("ADMIN"))) {
				logger.error("UNAUTHORIZED USER");
				return "redirect: ../main.do";
			}

			model.addAttribute("res", biz.searchFood(keyword, page));
			model.addAttribute("cnt", biz.getSearchCntFood(keyword));
			model.addAttribute("keyword", keyword);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("LOGIN DATA NOT FOUND");
			return "redirect: ../main.do";
		}

		return "admin/food/fsearch";
	}

	/*
	 * Exception Handler - Missing Parameter Exception
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public void handleMissingParams(MissingServletRequestParameterException e, HttpServletResponse response,
			HttpServletRequest request) {
		logger.error("MISSING PARAMETER");
		String url = request.getRequestURL().toString();
		String redirectURL = null;
		if (url.contains("admin/m")) {
			redirectURL = "/carebank/admin/meal.do?page=1";
		} else if (url.contains("admin/f")) {
			redirectURL = "/carebank/admin/food.do?page=1";
		}
		try {
			response.sendRedirect(redirectURL);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}