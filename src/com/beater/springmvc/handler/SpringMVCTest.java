package com.beater.springmvc.handler;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.beater.springmvc.entities.User;

//需要@Controller注解
@Controller
@RequestMapping("/WebMVC")
//@SessionAttributes(value="user",types=String.class)
public class SpringMVCTest {

	private static final String SUCCESS = "Success";
	
	@RequestMapping("testRedirect")
	public String testRedirect() {
		System.out.println("testRedirect..");
		return "redirect:/HelloWorld.jsp";
	}
	
	@RequestMapping("/testMyView")
	public String testMyView() {
		return "myView";
	}
	//有@ModelAttribute标记的方法，会在每个目标方法执行之前被SpringMVC调用
//	@ModelAttribute
//	public void getUser(@RequestParam("userName")String userName,Map<String,Object> map) {
//		System.out.println("modelAttribute method");
//		User user = new User(userName, "123456", 21);
//		System.out.println("从数据库中获取一个对象：" + user);
//		map.put("user1", user);
//	}
	
	/**
	 * 运行流程:
	 * 1. 执行 @ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放入到了 Map 中. 键为: user
	 * 2. SpringMVC 从 Map 中取出 User 对象, 并把表单的请求参数赋给该 User 对象的对应属性.
	 * 3. SpringMVC 把上述对象传入目标方法的参数. 
	 * 
	 * 注意: 在 @ModelAttribute 修饰的方法中, 放入到 Map 时的键需要和目标方法入参类型的第一个字母小写的字符串一致!
	 * 
	 * SpringMVC 确定目标方法 POJO 类型入参的过程
	 * 1. 确定一个 key:
	 * 1). 若目标方法的 POJO 类型的参数木有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
	 * 2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值. 
	 * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
	 * 1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到. 
	 * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰, 
	 * 若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
	 * 对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常. 
	 * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
	 * 会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
	 * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中. 
	 * 
	 * 源代码分析的流程
	 * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中.
	 * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
	 * 1). 创建 WebDataBinder 对象:
	 * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写. 
	 * *注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute 
	 * 的 value 属性值 
	 * 
	 * ②. 确定 target 属性:
	 * 	> 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok
	 * 	> *若不存在: 则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
	 * 获取 attrName 所对应的属性值. 若 session 中没有对应的属性值, 则抛出了异常. 
	 * 	> 若 Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key
	 * 和 attrName 相匹配, 则通过反射创建了 POJO 对象
	 * 
	 * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性. 
	 * 3). *SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel. 
	 * 近而传到 request 域对象中. 
	 * 4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参. 
	 */
	@RequestMapping("testModelAttribute")
	public String testModelAttribute(@ModelAttribute(value="user1")User user) {
		System.out.println("修改：" + user);
		return SUCCESS;
	}
	
	//@SessionAttributes只能作用于类的上面，不能修饰方法，可将模型数据放入session域对象中
	//value：指定属性名
	//types：指定属性的类型
	@RequestMapping("testSessionAttribute")
	public String testSessionAttribute(Map<String,Object> map) {
		User user = new User("tom","123456" , 20);
		map.put("user", user);
		map.put("email", "xx@qq.com");
		return SUCCESS;
	}
	//Map可以作为参数
	//效果与ModelAndView相同
	//ModelMap为ModelAndView的值，返回值为ModelAndView的键
	@RequestMapping("testMap")
	public String testMap(Map<String,Object> map) {
		map.put("name", Arrays.asList("tom","jerry"));
		return SUCCESS;
	}
	
	//目标方法的返回值可以是ModelAndView类型
	//ModelAndView可以使用viewName作为参数值
	//addObject可以用来添加Model数据到RequestScope中
	@RequestMapping("testModelAndView")
	public ModelAndView testModelAndView() {
		ModelAndView modelAndView = new ModelAndView(SUCCESS);
		modelAndView.addObject("time", new Date());
		return modelAndView;
	}
	/**
	 * 可以使用Servlet原生API作为目标方法的参数，具体支持以下类型
	 * HttpServletRequest
	 * HttpServletResponse 
	 * HttpSession 
	 * java.security.Principal  
	 * Locale 
	 * InputStream 
	 * OutputStream 
	 * Reader 
	 * Writer
	 * @throws IOException 
	 */
	@RequestMapping("testServletAPI")
	public void testServletAPI(HttpServletRequest request,HttpServletResponse response,Writer out) throws IOException {
		System.out.println(request.getServletContext().getContextPath());
		System.out.println(request.getServletPath());
		System.out.println(response.getWriter()==out);
		out.write("testServletAPI");
	}

	/**
	 * Spring MVC会按请求参数名和PoJo属性名进行自动匹配 自动为该对象填充属性值，支持级联属性 如：address.province
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("testPoJo")
	public String testPoJo(@ModelAttribute("user1")User user) {
		System.out.println(user);
		return SUCCESS;
	}

	// @CookieValue用法与@RequestParam相同
	@RequestMapping("testCookieValue")
	public String testCookieValue(
			@CookieValue(value = "JSESSIONID", required = false, defaultValue = "123456") String sessionId) {
		System.out.println(sessionId);
		return SUCCESS;
	}

	// @RequestHeader用法与@RequestParam相同
	@RequestMapping("testRequestHeader")
	public String testRequestHeader(
			@RequestHeader(value = "Accept-Language", required = false, defaultValue = "zh-cn") String acceptLanguage) {
		System.out.println(acceptLanguage);
		return SUCCESS;
	}

	// @RequestParam获取请求参数
	// value：参数名
	// required：是否为必须参数，默认为true
	// defaultValue：当请求参数为null是自动赋值
	@RequestMapping("testRequestParam")
	public String testRequestParam(
			@RequestParam(value = "userName", required = false, defaultValue = "beater") String userName) {
		System.out.println(userName);
		return SUCCESS;
	}

	// REST风格的URL
	// CRUD:
	// 新增：/order post
	// 修改：/order/1 put
	// 获取：/order/1 get
	// 删除：/order/1 delete
	// 发送put和delete请求的方法：
	// 1.需要配置HiddenHttpMethodFilter
	// 2.需要发送POST请求
	// 3.需要带隐藏域，name="_method" value="delete/put"
	@RequestMapping(value = "testREST/{id}", method = RequestMethod.PUT)
	@ResponseBody()
	public String testRESTPut(@PathVariable("id") int id) {
		System.out.println("testRESTPut" + id);
		return SUCCESS;
	}

	@RequestMapping(value = "testREST/{id}", method = RequestMethod.DELETE)
	/*
	 * TomCat8.0版本后service方法中不再支持put和delete 发起的请求是个RESTFul风格的请求，调用了RESTFul风格的PUT方法。
	 * 但是controller里testRestPUT返回的success字符串被映射到success.jsp。
	 * 因此spring认为这应该是个JSP接口，且JSP接口仅仅支持GET方法和POST方法。 所以系统提示提示了这个错误。
	 * 解决方法：使用@ResponseBody注解
	 */
	@ResponseBody()
	public String testRESTDelete(@PathVariable("id") int id) {
		System.out.println("testRESTDelete" + id);
		return SUCCESS;
	}

	@RequestMapping(value = "testREST", method = RequestMethod.POST)
	public String testRESTPost() {
		System.out.println("testRESTPost");
		return SUCCESS;
	}

	@RequestMapping(value = "testREST/{id}", method = RequestMethod.GET)
	public String testRESTGet(@PathVariable("id") int id) {
		System.out.println("testRESTGet" + id);
		return SUCCESS;
	}

	// @PathVariable可以映射url的占位符到目标方法的参数中
	@RequestMapping("testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") int id) {
		System.out.println(id);
		return SUCCESS;
	}

	// ant风格资源地址
	// ?:匹配文件名中的一个字符
	// *：匹配文件名中的任意字符
	// **：匹配多层路径
	@RequestMapping(value = "testAntRequestMapping/*")
	public String testAntRequestMapping() {
		return SUCCESS;
	}

	// param,headers属性可以约束请求参数和请求头的访问
	// username=??:代表请求参数一定要有username参数且该参数要等于xx
	// password：代表请求参数一定要有password参数
	@RequestMapping(value = "testParamsAndHeaders", params = { "username=beater", "password" }, headers = {
			"Accept-Encoding: gzip, deflate, br"})
	public String testParamsAndHeaders() {
		return SUCCESS;
	}

	// @RequestMapping可以注解类和方法
	// 类定义处：提供初步的请求映射信息。相对于WEB应用的根目录
	// 方法处：提供进一步的细分映射信息
	// 相对于类定义处的URL。若类定义处未标注@RequestMapping，则方法标记处的url相对于WEB应用的根目录
	@RequestMapping(value = "/testMethod", method = RequestMethod.POST)
	public String testMethod() {
		return SUCCESS;
	}

	// 配置@RequestMapping注解来映射请求的URL
	// 返回值会通过视图解析器解析为实际的物理视图，对于InternalResourceViewResolver视图解析器，会做以下的解析
	// prefix + returnval + suffix得到实际的物理视图，然后会做转发操作
	@RequestMapping("/helloWorld")
	public String helloWorld() {
		System.out.println("HelloWorld");
		return SUCCESS;
	}
}
