<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<s:form id="tempTerm" action="25501_execute.action">
    <s:hidden name="Term" />
    <s:hidden name="codProds" />
    <s:hidden name="giftCodes" />
    <s:hidden name="AccountNo" />
    <s:hidden name="state" />
    <s:hidden name="mode" />
    <s:token />
</s:form>
<sj:a id="tempDlgTerm" formIds="tempTerm" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:url var="urlGrid" action="25501_list" namespace="/json" escapeAmp="false">
    <s:param name="giftCode"><%=request.getParameter("giftCode")%></s:param>
    <s:param name="codProd"><%=request.getParameter("codProd")%></s:param>
    <s:param name="AccountNo"><%=request.getParameter("AccountNo")%></s:param>
	<s:param name="mode"><%=request.getParameter("mode")%></s:param>
    <s:param name="state"><%=request.getParameter("state")%></s:param>
</s:url>
<sjg:grid
    id="gridtable"
    caption=""
    dataType="json"
    href="%{urlGrid}"
    pager="true"
    pagerPosition="right"
    gridModel="gridTemplate"
    rowNum="15"
    rownumbers="true"
    altRows="true"
    autowidth="true"
    navigator="false"
    navigatorAdd="false"
    navigatorDelete="false"
    navigatorEdit="false"
    navigatorRefresh="false"
    navigatorSearch="false"
    onSelectRowTopics="selectRow" onGridCompleteTopics="gridComplete"
	>
    <sjg:gridColumn name="effectiveDate" index="effectiveDate" title="Effective Date" sortable="true" width="100" hidden="true"/>
    <sjg:gridColumn name="TERM" title="term" sortable="false" width="100"/>

</sjg:grid>
<script type="text/javascript">
    $(function() {
		var setValueFromGrid = 
			function(event, data, elementName, fieldName) { 
			var value = $(data).jqGrid("getCell", event.originalEvent.id, fieldName);
			$("#" + elementName).val(value); 
		};
			
        $("#gridtable").unsubscribe("selectRow");
        $("#gridtable").subscribe("selectRow", function(event, data) {
            var test = $.map(dlgParams, function(value, key) { return key; });
			
			setValueFromGrid(event, data, dlgParams.term, "TERM");
			setValueFromGrid(event, data, dlgParams.term, "ID");
			
            $("#frmMain_tax").val($("#taxPct").val());
            $("#tempTerm_Term").val($("#frmMain_Term").val());
            $("#tempTerm_codProds").val($("#codProds").val());
            $("#tempTerm_giftCodes").val($("#giftCodes").val());
            $("#tempTerm_AccountNo").val($("#frmMain_AccountNo").val());
            $("#tempTerm_mode").val("4:" + $("#modes").val());
            $("#tempTerm_state").val("TERM");
            
            $("#tempDlgTerm").click();
            labelParams = {};
            labelParams.onClose = function(giftPrice,MinAmount,maximumHold,id,holdAmount,programdetailid,opendate,Term,status,closeDate,giftGross,taxAmount){
                if(Term == ""){
                    //alert("test");
                    //$("#supervisor").val("");
                    var Reason = "Term Not Available";
					if(document.getElementById("errorpas0")){                    
                        removesRow3("errorpas0");   
                    }
                    $('#errAcctMessage').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="actionMessage" classname="actionMessage">'
                        + Reason +'</span></td></tr>');
                    $("#btnSubmit").button("disable");	
                    //e.preventDefault();
                } else {
                    $("#MinAmount").val(MinAmount);
                    $("#MaxAmount").val(maximumHold);
					$("#MinAmountIDR").val(MinAmount);
                    $("#MaxAmountIDR").val(maximumHold);

                    $("#frmMain_giftPrices").val(giftPrice);
                    $("#giftPrice").val(giftPrice);
					
                    $("#idProgram_details").val(programdetailid);
                    if(rdb.current == "add"){
						$("#frmMain_holdAmount").val(0);
						$("#idSequence").val(0);
					} else if (rdb.current == "delete"){
						$("#giftGross").val(giftGross);
						$("#taxAmount").val(taxAmount);
						$("#frmMain_holdAmounts").val(holdAmount);
						$("#holdAmount").val(holdAmount);						
						$("#idSequence").val(id);
						$("#frmMain_Canceldate").val(opendate);
					} else {
						$("#frmMain_holdAmounts").val(holdAmount);						
						$("#holdAmount").val(holdAmount);
						$("#idSequence").val(id);
					}
									
					if(status == "1"){
                        $("#frmMain_Status").val(status + "-Open");
					} else if(status == "2"){
						$("#frmMain_Status").val(status + "-Closed");
						$("#frmMain_Canceldate").val(closeDate);
					}
					$("#frmMain_Opendate").val(opendate);
					
					//$("#frmMain_holdAmounts").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
					$("#MinAmountIDR").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
					$("#MaxAmountIDR").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});					
                    $("#frmMain_giftPrices").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
					
					$("#frmMain_RangeholdAmount").val($("#MinAmountIDR").val() +" - "+ $("#MaxAmountIDR").val());
					if(document.getElementById("errorpas0")){                    
                        removesRow3("errorpas0");	
                    }
                }
            }
			
            $("#ph-dlg").dialog("close");
        });
        $("#gridtable").unsubscribe("gridComplete");
        $("#gridtable").subscribe("gridComplete", function(event, data) {
				if ($("#gridtable_pager_left #note").length == 0) {
				$("#divButtons").width($("#aprvGridTable").width());
				if ($('#gridtable').jqGrid('getGridParam', 'records') == 0) {
					$("#gridtable_pager_left").append("<span id='note' style='color:red'>Active Term Record not found</span>");
				}
				else {
					$("th.ui-th-column div").css({"white-space": "normal", "height" : "auto"});
					$("input.cbox").css("vertical-align", "middle");
					$("td[role='gridcell']").css("vertical-align", "middle");
					//$("#gview_aprvGridTable div.ui-jqgrid-bdiv").css("height", $('#aprvGridTable').jqGrid('getGridParam', 'records') * 23);				
				}
			}
            $("#ph-dlg").dialog({ width:Math.min(screen.availWidth-20, 400), height:$("#gbox_gridtable").height()+$("div.ui-dialog-titlebar.ui-widget-header").height()+44 });
            $("#ph-dlg").dialog({ position:"center" });
            $(data).jqGrid("setGridWidth", Math.min(screen.availWidth-20, 400)-22);
        });     
        
    });
</script>
