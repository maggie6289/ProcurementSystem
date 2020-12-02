package org.shop.controller.user;

import java.io.Reader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.shop.pojo.Hw;
import org.shop.pojo.Sh;
import org.shop.pojo.User;

import org.shop.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService u;

	@Autowired
	private HwService h;

	@Autowired
	private ShService s;

	// login
	@RequestMapping("tzlogin")
	public String tzlogin() {
		return "login";
	}

	// 登录验证
	@ResponseBody
	@RequestMapping("login")
	public String login(User user, HttpSession session, String requestDate) {
		Map map = new HashMap();
		JSONObject requestJson = JSONObject.fromObject(requestDate);
		map.put("name", requestJson.getString("name"));
		map.put("password", requestJson.getString("password"));
		user.setName(requestJson.getString("name"));
		user.setPassword(requestJson.getString("password"));
		User user2 = u.login(user);
		session.setAttribute("t2", user2.getT2());
		session.setAttribute("id", user2.getId());
		if (user2 == null) {
			Map reMap = new HashMap();
			reMap.put("succ", "false");
			JSONObject jsonObject = JSONObject.fromObject(reMap);
			return jsonObject.toString();
		} else {
			System.out.println("user2" + user2);
			session.setAttribute("name", user.getName());
			session.setAttribute("id", user2.getId());
			session.setAttribute("t1", user2.getT1());
			Map reMap = new HashMap();
			reMap.put("succ", "true");
			JSONObject jsonObject = JSONObject.fromObject(reMap);
			return jsonObject.toString();
		}

	}

	@RequestMapping("sy")
	public String denglu(HttpSession session, String t2, Model model, String name, User user) {
		int qx = (int) session.getAttribute("t1");
		// model.addAttribute("list", u.findall());
		if (qx == 0) {
			model.addAttribute("list", u.findall(user));
			return "gly/yh";
		} else if (qx == 1) {
			model.addAttribute("list", u.findall(user));
			return "gly/yh";
		} /*else if (qx == 2) {
			return "redirect:tzckxs";
		}*/ else if (qx == 3) {
			return "redirect:tzkccg";
		}
		return "login";
	}
	
	

	@RequestMapping("touser")//到用户界面
	public String touser(User user) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		user.setDate(df.parse(time));
		user.setT1(4);
		user.setT2(0);
		u.touser(user);
		return "redirect:tzlogin";
	}

	@RequestMapping("delete")
	public String delete(int id) {
		u.delete(id);
		return "redirect:sy";
	}

	@RequestMapping("tjyh")//添加用户
	public String tzyh() {
		return "gly/upyh";
	}

	@RequestMapping("insert")
	public String insert(User user) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		user.setDate(df.parse(time));
		u.touser(user);
		return "redirect:sy";
	}
	
	@RequestMapping("insert1")
	public String insert1(User user) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		user.setDate(df.parse(time));
		u.touser(user);
		return "redirect:tzlogin";
	}

	@RequestMapping("tzup")
	public String tzup(int id, Model mode) {
		User user = u.findid(id);
		mode.addAttribute("user", user);
		return "gly/user";
	}
	
	@RequestMapping("tzzup")
	public String tzzup(Model model,HttpSession session) {
		int  id=(int) session.getAttribute("id");
		User user = u.findid(id);
		model.addAttribute("user", user);
		return "gly/user";
	}

	@RequestMapping("upyh")
	public String upyh(User user) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		u.userup(user);
		return "redirect:sy";
	}

	@RequestMapping("tzhw")//跳转货物界面
	public String tzhw(Model model, Hw hw) {
		hw.setSj(1);
		hw.setSh(1);
		model.addAttribute("list", h.sp(hw));
		return "gly/hw";
	}

	@RequestMapping("xj")//新建采购表
	public String spxj(int id, Hw hw) throws ParseException {
		hw.setSh(1);
		hw.setSj(0);
		hw.setId(id);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		hw.setDate(df.parse(time));
		h.spxj(hw);
		return "redirect:tzhw";
	}

	@RequestMapping("tzkc")
	public String tzkc(Model model, Hw hw) {
		hw.setSj(0);
		hw.setSh(1);
		model.addAttribute("list", h.sp(hw));
		return "gly/kc";
	}

	@RequestMapping("sj")
	public String spsj(int id, Hw hw) throws ParseException {
		hw.setSh(1);
		hw.setSj(1);
		hw.setId(id);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		hw.setDate(df.parse(time));
		h.spxj(hw);
		return "redirect:tzkc";
	}

	@RequestMapping("tzrk")
	public String tzrk() {
		return "gly/rkd";
	}

	@RequestMapping("rk")
	public String zjrk(Hw hw, HttpSession session) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		hw.setDate(df.parse(time));
		hw.setSj(0);
		hw.setSh(0);
		hw.setPrivilege(1);
		String zrr = (String) session.getAttribute("name");
		hw.setZrr(zrr);
		h.xjrk(hw);
		return "redirect:tzrk";
	}

	@RequestMapping("tzrksh")
	public String tzrush(Model model, Hw hw) {
		hw.setSh(0);
		hw.setSj(0);
		model.addAttribute("list", h.sp(hw));
		return "gly/rksh";
	}
	
	@RequestMapping("lookStatus") //updateHw
	public String lookStatus(Model model, Hw hw) {
		hw.setSh(0);
		hw.setSj(0);
		model.addAttribute("list", h.sp(hw));
		return "gly/rksh1";
	}

	@RequestMapping("lookStatus1") //updateHw
	public String lookStatus1(Model model, Hw hw) {
		hw.setSh(0);
		hw.setSj(0);
		model.addAttribute("list", h.sp(hw));
		return "cg/rksh1";
	}
	
	@RequestMapping("updateHw") 
	public String updateHw(Model model, Hw hw) {
		h.updateHw(hw);
		model.addAttribute("list", h.sp(hw));
		return "gly/rksh";
	}
	
	@RequestMapping("updateHw1") 
	public String updateHw1(Model model, Hw hw) {
		h.updateHw(hw);
		model.addAttribute("list", h.sp(hw));
		return "cg/rksh1";
	}
	
/*	@RequestMapping("fenlei")
	public String fenlei(Model model, Hw hw) {
		hw.setPrivilege(1);
		h.updatePrivilege(hw);
		model.addAttribute("list", h.sp(hw));
		return "gly/rksh";
	}*/
	
	@RequestMapping("kuangjia")
	public String kuangjia(Model model, Hw hw) {
		hw.setPrivilege(1);
		hw.setIsFrame("0");
		h.updatePrivilege(hw);
		model.addAttribute("list", h.sp(hw));
		return "gly/rksh";
	}
	
	@RequestMapping("jujue")
	public String jujue(Model model, Hw hw) {
		hw.setPrivilege(3);
		h.updatePrivilege(hw);
		model.addAttribute("list", h.sp(hw));
		return "gly/rksh";
	}
	
	@RequestMapping("bianji")
	public String bianji(Model model, Hw hw) {
		Hw list = h.dy(hw.getId());
		model.addAttribute("list", list);
		return "gly/rkd1";
	}
	
	@RequestMapping("bianji1")
	public String bianji1(Model model, Hw hw) {
		Hw list = h.dy(hw.getId());
		model.addAttribute("list", list);
		return "cg/rkd1";
	}
	
	@RequestMapping("bianji2")
	public String bianji2(Model model, Hw hw) {
		Hw list = h.dy(hw.getId());
		model.addAttribute("list", list);
		return "cg/rkd2";
	}
	
	@RequestMapping("feikj")
	public String feikj(Model model, Hw hw) {
		hw.setPrivilege(1);
		hw.setIsFrame("1");
		h.updatePrivilege(hw);
		model.addAttribute("list", h.sp(hw));
		return "gly/rksh";
	}
	
	@RequestMapping("rks")
	public String rk(int id, Hw hw) throws ParseException {
		//hw.setSj(0);
		//hw.setSh(1);
		hw.setId(id);
		hw.setPrivilege(2);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		hw.setDate(df.parse(time));
		h.spxj(hw);
		return "redirect:tzrksh";
	}

	@RequestMapping("tzck")
	public String tzck(Model model, Hw hw) {
		hw.setSh(1);
		hw.setSj(1);
		List list = h.sp(hw);
		model.addAttribute("list", list);
		return "gly/ckd";
	}

	@RequestMapping("ckid")
	public String ckid(int id, Model model, HttpSession session) {
		Hw list = h.dy(id);
		int jj = list.getNumber();
		session.setAttribute("jj", jj);
		session.setAttribute("gg", list.getMoney());
		model.addAttribute("list", list);
		return "gly/ckl";
	}

	@RequestMapping("ckl")
	public String ckl(Sh sh, HttpSession session, Hw hw) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		sh.setDate(df.parse(time));
		sh.setSh(0);
		int g = sh.getJg() - sh.getMoney();
		System.out.println(g);
		sh.setLr(sh.getSl() * g);
		String name = (String) session.getAttribute("name");
		sh.setZrr(name);
		s.ck(sh);
		return "redirect:tzck";
	}

	@RequestMapping("tzcksh")
	public String cksss(Model model) {
		List list = s.cc(0);
		model.addAttribute("list", list);
		return "gly/cksh";

	}

	@RequestMapping("cksh")
	public String cksh(int id, Sh sh, Hw hws, HttpSession session) throws ParseException {
		sh.setSh(1);
		sh.setHw(s.hwss(id).getHw());
		s.cksh(sh);
		Sh ssSh = new Sh();
		String name = s.hwss(id).getHw();
		List list = s.hws(name);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		hws.setDate(df.parse(time));
		Hw hsHw = new Hw();
		hsHw = h.dys(name);
		int ye = hsHw.getNumber();
		int l = ye - s.hwss(id).getSl();
		hws.setNumber(l);
		hws.setName(name);
		h.ckkk(hws);
		return "redirect:tzcksh";
	}

	@RequestMapping("sb")
	public String sb(int id, HttpSession session) {
		s.jj(id);
		return "redirect:tzrksh";
	}

	@RequestMapping("tzbb")
	public String bb(Model model) {
	     Map map = new HashMap(); 
	     List list=new ArrayList<>();
		 //list = s.bb();
	     list=s.bbs();
	    // String str=String.join("=", list);
	     
	//	System.out.println(str);
		JSONArray json = JSONArray.fromObject(list);
		
		model.addAttribute("list", list);
		model.addAttribute("json", json);
		System.out.println(json);
		System.out.println(list);
		
		return "gly/bb";
	}
	//********************************采购******************************************
	@RequestMapping("tzkccg")
	public String tzkccg(Model model, Hw hw) {
		hw.setSj(0);
		hw.setSh(1);
		model.addAttribute("list", h.sp(hw));
		return "cg/rkd";
	}
	@RequestMapping("tzrkcg")
	public String tzrkcg() {
		return "cg/rkd";
	}
	
	@RequestMapping("rkcg")
	public String zjrkcg(Hw hw, HttpSession session) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		hw.setDate(df.parse(time));
		hw.setSj(0);
		hw.setSh(0);
		String zrr = (String) session.getAttribute("name");
		hw.setZrr(zrr);
		h.xjrk(hw);
		return "redirect:tzrkcg";
	}
	
	@RequestMapping("tzzupss")
	public String tzzupss(Model model,HttpSession session) {
		int  id=(int) session.getAttribute("id");
		User user = u.findid(id);
		model.addAttribute("user", user);
		return "cg/user";
	}
	
	//*************************************销售**************************************
	@RequestMapping("tzckxs")
	public String tzckxs(Model model, Hw hw) {
		hw.setSh(1);
		hw.setSj(1);
		List list = h.sp(hw);
		model.addAttribute("list", list);
		return "xs/ckd";
	}
	@RequestMapping("ckidxs")
	public String ckidxs(int id, Model model, HttpSession session) {
		Hw list = h.dy(id);
		int jj = list.getNumber();
		session.setAttribute("jj", jj);
		session.setAttribute("gg", list.getMoney());
		model.addAttribute("list", list);
		return "xs/ckl";
	}
	
	@RequestMapping("cklxs")
	public String cklxs(Sh sh, HttpSession session, Hw hw) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		sh.setDate(df.parse(time));
		sh.setSh(0);
		int g = sh.getJg() - sh.getMoney();
		System.out.println(g);
		sh.setLr(sh.getSl() * g);
		String name = (String) session.getAttribute("name");
		sh.setZrr(name);
		s.ck(sh);
		return "redirect:tzckxs";
	}
	
	
	@RequestMapping("tzzups")
	public String tzzups(Model model,HttpSession session) {
		int  id=(int) session.getAttribute("id");
		User user = u.findid(id);
		model.addAttribute("user", user);
		return "xs/user";
	}
	
	

	@RequestMapping("upyhs")
	public String upyhs(User user) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
		String time = df.format(System.currentTimeMillis());
		user.setT1(0);
		user.setT2(0);
		user.setDate(df.parse(time));
		u.userup(user);
		return "redirect:tzlogin";
	}
}
