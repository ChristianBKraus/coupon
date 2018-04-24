package jupiterpa.coupon.intf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import jupiterpa.coupon.domain.model.*;
import jupiterpa.coupon.domain.service.*;


@RequestMapping(path = CouponController.PATH)
@RestController
@Api(value="coupon", description="Coupon Controller")
public class CouponController {
    public static final String PATH ="/coupon";
    
    @Autowired ActionRepo withdrawRepo;
    @Autowired BalanceRepo balanceRepo;
    @Autowired CouponService service;
    
    @GetMapping("/balance/{name}")
    @ApiOperation(value = "GET current Balance of client")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved Balance")
    })
    public int get(@PathVariable String name) {  
    	BalanceEntity entity = balanceRepo.findByName(name);
    	if (entity != null) {
    		return entity.getValue();
    	} else {
    		return 0;
    	}
    }
    
    @GetMapping("/balance")
    @ApiOperation(value = "GET current Balance of all clients")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved Balances")
    })
    public List<BalanceEntity> getAll() {  
    	return balanceRepo.findAllByOrderByName();
    }
    
    @PostMapping("/withdraw/{name}")
    @ApiOperation(value = "Create new Withdrawal")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Created Successfully")
    })
    public int postWithrdaw(@PathVariable String name) {
    	return service.withdraw(name);
    }

    @PostMapping("/issue/{name}/{amount}")
    @ApiOperation(value = "Issue new Coupon of x minutes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Created Successfully")
    })
    public int postIssue(@PathVariable String name, @PathVariable int amount) {
    	return service.issue(name,amount);
    }

}
