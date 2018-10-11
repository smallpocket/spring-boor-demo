package com.demo.test.controller;

import com.demo.test.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * 进行springboot里面的test整合
 *
 * @RunWith 在JUnit中有很多个Runner，他们负责调用你的测试代码，每一个Runner都有各自的特殊功能，
 * @Author : Hyper
 * @Time : 2018/10/10 23:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseControllerTest {

    /**
     * 注解详解:
     *
     * @BeforeClass :所有测试方法前执行一次，一般在其中写上整体初始化的代码
     * @AfterClass 在所有测试方法后执行一次，一般在其中写上销毁和释放资源的代码
     * @Test(timeout = 1000) 测试方法执行超过1000毫秒后算超时，测试将失败
     * @Test(expected = Exception.class) 测试方法期望得到的异常类，如果方法执行没有抛出指定的异常，则测试失败
     * @Ignore(“not ready yet”)        执行测试时将忽略掉此方法，如果用于修饰类，则忽略整个类
     * @Test 编写一般测试用例
     */
    @Test
    public void testOne() {
        System.out.println("test hello 1");
    }

    /**
     * @Before 在每个测试方法前执行，一般用来初始化方法（比如我们在测试别的方法时，类中与其他测试方法共享的值已经被改变，为了保证测试结果的有效性，我们会在@Before注解的方法中重置数据）
     */
    @Before
    public void testBefore() {
        System.out.println("before");
    }

    /**
     * @After 在每个测试方法后执行，在方法执行完成后要做的事情
     */
    @After
    public void testAfter() {
        System.out.println("after");
    }

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;


    /**
     * 构造mockMvc,初始化mock
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * 进行参数的请求,模拟mock
     *
     * @throws Exception
     */
    @Test
    public void getMap() throws Exception {
        //调用接口
        MvcResult result = (MvcResult) mockMvc.perform(get("/test/get")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //传入参数
                .param("userId", "11").
                        param("userName", "henry")
                //接收的类型
                .accept(MediaType.APPLICATION_JSON))
                //判断接收到的状态是否是200
                .andExpect(status().isOk())
                //打印内容
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //匹配返回值中的内容
                .andExpect(content().string(Matchers.containsString("OK")))
                //使用jsonPath解析返回值，判断具体的内容 
                //需要学习jsonpath
                .andExpect(jsonPath("$.errcode", is(0)));
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode, 200);
    }

    /**
     * 测试添加用户接口
     *
     * @throws Exception
     */
    @Test
    public void testAddUser() throws Exception {
        //构造添加的用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setName("testuser2");
        userInfo.setAge(29);
        userInfo.setAddress("北京");
        ObjectMapper mapper = new ObjectMapper();
        //调用接口，传入添加的用户参数
        mockMvc.perform(post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                //将整个userInfo当做参数传入
                .content(mapper.writeValueAsString(userInfo)))
                .andExpect(status().isOk())
                //使用jsonPath解析返回值，判断具体的内容
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //判断返回值，是否达到预期，
                // 测试示例中的返回值的结构如下{"errcode":0,"errmsg":"OK","p2pdata":null}
                .andExpect(jsonPath("$.errcode", is(0)))
                .andExpect(jsonPath("$.p2pdata", notNullValue()))
                .andExpect(jsonPath("$.p2pdata.id", not(0)))
                .andExpect(jsonPath("$.p2pdata.name", is("testuser2")));

    }

    /**
     * 如果在post的接口中接收的参数为对象的话，可以进行（Json对象或者实体类的）封装，即注释掉的内容。
     *
     * @throws Exception
     */
    @Test
    public void testInfo() throws Exception {
//    JSONObject param = new JSONObject() ;
//    param.put("userId", "11");
//    param.put("userName", "henry");
//    String jsonstr = param.toString() ;
//    System.out.println("================================请求入参："+jsonstr);
        RequestBuilder request = post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("userId", "11").param("userName", "henry");
//    RequestBuilder request = MockMvcRequestBuilders.post("/classmanage/signOutCurrentClass")
//          .contentType(MediaType.APPLICATION_JSON)
//          .content(jsonstr).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("返回结果：" + status);
        System.out.println(content);
    }

}