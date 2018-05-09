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
@WithMockUser(roles="USER")
@ActiveProfiles({"test"})
public class UserIntegrationTest { 
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
    public void withdraw() throws Exception {
    	String name = "Name";

        BalanceEntity entity = new BalanceEntity(name,10);
        entity = balanceRepo.insert(entity);

//      Get
    	mockMvc.perform( get(PATH + "/balance/" + name) )
        .andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(content().string("10"));
        
//      Post
    	mockMvc.perform( post(PATH + "/withdraw/" + name) )
        .andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(content().string("9"));
    	
//      Get
    	mockMvc.perform( get(PATH + "/balance/" + name) )
        .andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(content().string("9"));
    	
    	List<BalanceEntity> balances = balanceRepo.findAll();
    	assertTrue(balances.size() == 1);
    	entity = balances.get(0);
    	assertTrue(entity != null);
    	assertTrue(entity.getName().matches(name));
    	assertTrue(entity.getValue() == 9);
    	
//      GetAll    	
    	mockMvc.perform( get(PATH + "/balance") )
        .andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[0].name").value(name))
        .andExpect(jsonPath("$[0].value").value(9));
    }    
    
    @Test
    public void withdrawWithWrongName() throws Exception {
    	String name = "Name";
    	String wrong = "Name1";

        BalanceEntity entity = new BalanceEntity(name,10);
        balanceRepo.insert(entity);

//      Get
    	mockMvc.perform( get(PATH + "/balance/" + wrong) )
        .andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(content().string("0"));
        
//      Post
    	mockMvc.perform( post(PATH + "/withdraw/" + wrong) )
        .andExpect(status().isNotFound());
    }
    
    @Test 
    public void issueCouponAsUser() throws Exception {
    	String name = "Name1";

//      Post Issue
    	try {
			mockMvc.perform( post(PATH + "/issue/" + name + "/10") )
			.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    	
    	List<BalanceEntity> balances = balanceRepo.findAll();
    	assertTrue(balances.size() == 0);

    	List<ActionEntity> action = actionRepo.findAll();
    	assertTrue(action.size() == 0);
}

//    private String toJson(Object object) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(object);
//    }
}