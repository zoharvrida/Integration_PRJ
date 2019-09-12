<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio 
	id="rbBS" 
	name="rbBS" 
	list="{'Inquiry':'Payment','Add':'Reinquiry'}" 
	disabled="false" value="Inquiry"
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
                $(window).resize();
                rdb.resetDisabled();
		rdb.resetReadonly();
		rdb.resetDisableButton();
		console.log('BUTTONS DOM READY CALLED!!!');
		
                rdb.setOnChange(
                    function() {
                        var _state = getState();
                        if(_state == -1) {
                            _state = 0;
                        }
                        console.log('RDB onChange 1, state: ' + _state);
                        changeAction("frmPayment", "32101", "_inquiry");

                        $(".cls-row-button").hide();
                        setView(0, _state);
                        //setView(0, 0);
                    }, 
                    function() {
                        console.log('RDB onChange 2, state: ' + getState() + ', supposed state: ' + getSupposedState());
                        changeAction("frmMain", "32101", "_reinquiry");

                        $(".cls-row-button").hide();
                        setView(1, 0);
                    }
                );
		
		rdb.setMenuAccess('1111', '1111');
	});
	
</script>