package jupiterpa.coupon;

import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import jupiterpa.coupon.domain.model.*;
import jupiterpa.coupon.intf.controller.CouponController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles="ADMIN")
@ActiveProfiles({"test"})
public class AdminIntegrationTest { 
	final String PATH = CouponController.PATH; 

	@Autowired private MockMvc mockMvc;
	@Autowired private BalanceRepo balanceRepo;
	@Autowired private ActionRepo actionRepo;
	
	@Before
	public void ResetDB() {
		balanceRepo.deleteAll();
		actionRepo.deleteAll();
	}
	    
    @Test 
    public void issueCouponAsAdmin() throws Exception {
    	String name = "Name1";

//      Post Issue
    	try {
			mockMvc.perform( post(PATH + "/issue/" + name + "/10") )
			.andExpect(status().isOk())
			.andExpect(content().string("10"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    	
    	List<BalanceEntity> balances = balanceRepo.findAll();
    	assertTrue(balances.size() == 1);

    	List<ActionEntity> action = actionRepo.findAll();
    	assertTrue(action.size() == 1);

//      GetAll    	
    	mockMvc.perform( get(PATH + "/balance") )
        .andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[0].name").value(name))
        .andExpect(jsonPath("$[0].value").value(10));
    }

    @Test 
    public void issueMultipleCoupon() throws Exception {
    	String name1 = "Name1";
    	String name2 = "Name2";

//      Post Issue: Name 1
    	try {
			mockMvc.perform( post(PATH + "/issue/" + name1 + "/10") )
			.andExpect(status().isOk())
			.andExpect(content().string("10"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

//      Post Issue: Name 2
    	try {
			mockMvc.perform( post(PATH + "/issue/" + name2 + "/5") )
			.andExpect(status().isOk())
			.andExpect(content().string("5"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    	
//      Post Issue: Name 1 - again
    	try {
			mockMvc.perform( post(PATH + "/issue/" + name1 + "/5") )
			.andExpect(status().isOk())
			.andExpect(content().string("15"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    	
    	List<BalanceEntity> balances = balanceRepo.findAll();
    	assertTrue(balances.size() == 2);

    	List<ActionEntity> action = actionRepo.findAll();
    	assertTrue(action.size() == 3);

//      GetAll    	
    	mockMvc.perform( get(PATH + "/balance") )
        .andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[0].name").value(name1))
        .andExpect(jsonPath("$[0].value").value(15))
        .andExpect(jsonPath("$[1].name").value(name2))
        .andExpect(jsonPath("$[1].value").value(5));
    }
    
//    private String toJson(Object object) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(object);
//    }
}