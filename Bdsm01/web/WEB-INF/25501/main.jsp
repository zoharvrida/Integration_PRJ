<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <s:url var="ajaxUpdateTitle" action="25501_title" />
    <s:url var="ajaxUpdateButtons" action="25501_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />

	<s:form id="tempValidation" action="25501_execute.action">
        <s:hidden name="acctSearch" />
        <s:hidden name="mode" />
		<s:token />
    </s:form>
    <sj:a id="tempDlgValidation" formIds="tempValidation" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

	<%-- Authentication --%>
	<s:form id="tempFrmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="25501" />
		<s:token />
	</s:form>
	<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

	<s:form id="tempFrmDlgAmortOpeningAcc" action="dlg">
        <s:hidden name="dialog" value="dlgAmortizeOpeningGiftDelete" />
		<s:hidden name="mode"/>
		<s:hidden name="AccountNo"/>
        <s:hidden name="state"/>
		<s:token />
	</s:form>
	<sj:a id="tempFrmDlgAmortOpeningAcc" formIds="tempFrmDlgAmortOpeningAcc" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

	<s:form id="tempFrmDlgAmortOpening" action="dlg">
        <s:hidden name="dialog" value="dlgAmortizeOpeningGiftAdd" />
		<s:hidden name="giftCode" />
		<s:hidden name="codProd"/>
		<s:hidden name="mode"/>
		<s:hidden name="AccountNo"/>
        <s:hidden name="state"/>
		<s:token />
	</s:form>
	<sj:a id="tempFrmDlgAmortOpening" formIds="tempFrmDlgAmortOpening" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

	<s:form id="tempFrmDlgAmortTerm" action="dlg">
		<s:hidden name="dialog" value="dlgAmortizeTerm"/>
		<s:hidden name="giftCode" />
		<s:hidden name="codProd"/>
		<s:hidden name="mode"/>
        <s:hidden name="AccountNo"/>
        <s:hidden name="state"/>
		<s:token />
	</s:form>
	<sj:a id="tempFrmDlgAmortTerm" formIds="tempFrmDlgAmortTerm" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError"/>
    <s:form id="frmMain" name="frmMain" action="25501" method="post" validate="true" theme="css_xhtml">

        <fieldset id="OpenGift" class="ui-widget-content ui-corner-all" style="border-radius: 1px">
            <legend class="ui-widget-header ui-corner-all"><s:label key="label.amortize.fieldset.legend.gifOpening" cssStyle="display:none"/></legend>
            <table id="AcctVal">
                <tbody id="body1">

                    <tr id="acct0">
                        <td colspan="1">
                            <s:label key="label.amortize.Account.no" cssStyle="display:none"/>
                        </td>
                        <td>
							<s:textfield
								name="AccountNo" 
                                size="20" 
                                maxlength="16" 
								cssClass="ui-widget ui-widget-content" 
								disabled="false"
								/>
                        </td>
                        <td>
							<sj:a
								name = "btnFindAccount"
								id="btnFindAccount"
								button="true"
                                disabled="false"
								>
								<span id="AccountSpan">...</span>
							</sj:a>
                        </td>

                        <td colspan="3">
                            &nbsp;
                        </td>
                        <td class="tdLabel">
                            <s:label key="label.amortize.Account.product" cssStyle="display:none"/>
                        </td>
                        <td>
                            <s:textfield
                                name="codProd"
                                size="60"
                                maxlength="60"
                                cssClass="ui-widget ui-widget-content"
                                disabled="true"
                                />
						</td>
						<td class="errorMessage">

						</td>
					</tr>
					<tr id="giftVal">
						<td	class="tdLabel">
                            <s:label key="label.amortize.Account.code" cssStyle="display:none"/>
                        </td>
                        <td>
							<s:textfield
								name="giftCode" 
                                size="30" 
                                maxlength="30" 
								cssClass="ui-widget ui-widget-content" 
								disabled="false"
								/>
							<sj:a
								id="btnFindgiftCode"
								button="true"
								disabled="false"
								>
								...
							</sj:a>
                        </td>
                        <td colspan="4">
                            &nbsp;
                        </td>
                        <td class="tdLabel">
                            <s:label key="label.amortize.Account.program" cssStyle="display:none"/>
                        </td>
                        <td>
							<s:textfield
                                name="programID" 
                                size="50" 
                                maxlength="50" 
								cssClass="ui-widget ui-widget-content" 
								disabled="false"
								/>
                        </td>
                    </tr>
				</tbody>
			</table>
		</fieldset>
        <fieldset id="OpenGiftDetails" class="ui-widget-content ui-corner-all" style="border-radius: 1px">
            <legend class="ui-widget-header ui-corner-all"><s:label key="label.amortize.fieldset.legend.OpeningDetail" cssStyle="display:none"/></legend>	
            <table id="detailVal">
                <tbody id="body2">
                    <tr width="100%" id="termVal">
                        <td class="tdLabel">
                            <s:label key="label.amortize.Account.term" cssStyle="display:none"/>
                        </td>
                        <td>
							<s:textfield
                                name="Term" 
                                size="3" 
                                maxlength="3" 
								cssClass="ui-widget ui-widget-content" 
								disabled="false"
								/>
							<sj:a
								id="btnFindTerm"
								button="true"
								disabled="false"
								>
								...
							</sj:a>
                        </td>
                        <td	class="tdLabel">
                            <s:label key="label.amortize.Account.status" cssStyle="display:none"/>
                        </td>
                        <td>
							<s:textfield
                                name="Status" 
                                size="10" 
                                maxlength="10" 
								cssClass="ui-widget ui-widget-content" 
								disabled="false"
								/>
                        </td>
                    </tr>
                    <tr id="date1">
                        <td	class="tdLabel">
							<s:label key="label.amortize.Account.openDate" cssStyle="display:none"/>
                        </td>
                        <td id="dateOpen">
							<s:textfield
                                name="Opendate" 
                                size="12" 
                                maxlength="12" 
								cssClass="ui-widget ui-widget-content" 
								disabled="false"
								/>
                        </td>
                        <td	id="closepad" class="tdLabel" colspan="2">
                            <s:label key="label.amortize.Account.closedDate" cssStyle="display:none"/>
                        </td>
                        <td id="dateClose">								
							<s:textfield
                                name="Canceldate" 
                                size="12" 
                                maxlength="12" 
								cssClass="ui-widget ui-widget-content"
                                disabled="false"
								/>
                        </td>
                    </tr>
                    <tr>
                        <td	class="tdLabel">
                            <s:label key="label.amortize.Account.tax" cssStyle="display:none"/>
                        </td>
                        <td>
                            <s:textfield
                                name="tax" 
                                size="6" 
                                maxlength="6" 
                                cssClass="ui-widget ui-widget-content"
                                disabled="false"
                                />
                        </td>	
                    </tr>
                    <tr id="holdVal">
                        <td	class="tdLabel">
                            <s:label key="label.amortize.Account.holdAmount" cssStyle="display:none"/>
                        </td>
                        <td>
							<s:textfield
                                name="holdAmounts" 
                                size="20" 
                                maxlength="20" 
								cssClass="ui-widget ui-widget-content"
                                disabled="false"
								/>
                        </td>
                        <td	class="tdLabel">
                            <s:label key="label.amortize.Account.rangeHold" cssStyle="display:none"/>
                        </td>
                        <td>
							<s:textfield
                                name="RangeholdAmount" 
                                size="50" 
                                maxlength="50" 
								cssClass="ui-widget ui-widget-content" 
								disabled="false"
								/>
                        </td>
                    </tr>
                    <tr>&nbsp;</tr>
                    <tr>
                        <td />
                        <td align="right" colspan="8">
                            <sj:submit
                                id="btnSubmit" 
                                formIds="frmMain" 
                                buttonIcon="ui-icon-gear"
								button="true"
                                key="button.save"  
                                targets="ph-main"
                                onBeforeTopics="beforeSubmit" 
                                disabled="true"
                                />
                        </td>
                        <td colspan="4" />
                    </tr>
                </tbody>
            </table>
        </legend>		
    </fieldset>

	<s:hidden name="modes" id="modes"/>
	<s:hidden name="giftName" id="giftName"/>
    <s:hidden name="giftCodes" id="giftCodes"/>
    <s:hidden name="giftPrice" id="giftPrice"/>
    <s:hidden name="taxPct" id="taxPct"/>
    <s:hidden name="accrualID" id="accrualID"/>

    <s:hidden name="status" id="status"/>
    <s:hidden name="programIds" id="programIds"/>
	<s:hidden name="programName" id="programName"/>
    <s:hidden name="type" id="type"/>
	<s:hidden name="MinAmount" id="MinAmount"/>
	<s:hidden name="MaxAmount" id="MaxAmount"/>

	<s:hidden name="holdAmount" id="holdAmount"/>

	<s:hidden name="MinAmountIDR" id="MinAmountIDR"/>
	<s:hidden name="MaxAmountIDR" id="MaxAmountIDR"/>

	<s:hidden name="codProds" id="codProds" />
	<s:hidden name="giftGross" id="giftGross" />
	<s:hidden name="taxAmount" id="taxAmount" />
	<s:hidden name="voucherType" id="voucherType" />

	<s:hidden name="namProducts" id="namProducts" />
	<s:hidden name="idSequence" id="idSequence"/>
	<s:hidden name="idProgram_details" id="idProgram_details"/>

    <s:hidden name="dateOpening" value="%{#session.dtBusiness}" />
	<s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
	<s:hidden name="idMaintainedSpv" />

	<s:token />
</s:form>
<br>

<script type="text/javascript">
	//$('#OpenGiftDetails').attr('style','display:none');
    function removesRow3(rowid){
        var row =  document.getElementById(rowid);
        //alert(rowid);
        row.parentNode.removeChild(row);
    }
	
	function fillValidation(){
		var holdAmount = this.$("#holdAmount").val();
		var bellowAmount = this.$("#MinAmount").val();
		var maxHold = this.$("#MaxAmount").val(); 
		//alert(maxHold + " " + holdAmount);
		if(document.getElementById("errorpas0")){                    
			return false;  
		}		
		if($("#frmMain_AccountNo").val() == ""){
			$('Table#AcctVal tbody#body1 tr#acct0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
				+"Account No Cannot be Blank"+'</span></td></tr>');	
			return false;
		} else {
			if($("#frmMain_Term").val() == ""){
				$('Table#detailVal tbody#body2 tr#termVal').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
					+"Term cannot be Blank"+'</span></td></tr>');	
				return false;
			} else {
				if($("#frmMain_programID").val() == "" || $("#frmMain_giftCode").val() == ""){
					$('Table#AcctVal tbody#body1 tr#giftVal').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
						+"Gift Code must be Validated"+'</span></td></tr>');	
					return false;
				} else {
					if($("#frmMain_holdAmounts").val() == "" && $("#holdAmount").val() == ""){
						$("#frmMain_holdAmounts").val("0.00");
						$("#holdAmount").val("0.00");
					}
					return true;
				} 
				/*else {
					
					if(holdAmount > maxHold){
					$('Table#detailVal tbody#body2 tr#holdVal').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="actionMessage" classname="actionMessage">'
						+"Hold Amount must Be Within Range"+'</span></td></tr>');
					return true;
					} else if(holdAmount < bellowAmount){
					$('Table#detailVal tbody#body2 tr#holdVal').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
						+"Hold Amount must higher than Minimum Range"+'</span></td></tr>');
					return false;
					} else {
					return true;	
					}
				}*/
			}
		}
	}
		$(function() {
			//$("#tabbedPanel").hide();
			//$('#tabbedPanel ul').attr('style', 'display:none');
			if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			}
			else {
				$("#rbBS").data("rdb").callCurrent();
				if ($("#actionError").length == 0) {
					$("#rbBS").data("rdb").clear(false);
				}
			}
			
			//$("#checkS").val("3");
			//$("#frmMain_IDBATCH").parent().parent().append('&nbsp;').append($("#btnFind").detach());
			$("#priceHide").hide();
			$("#AccountSpan").val("...");
			$("#frmMain_AccountNo").parent().append($("#btnFindAccount").detach());
			$("#frmMain_giftCode").parent().append($("#btnFindgiftCode").detach());
			$("#frmMain_Term").parent().append($("#btnFindTerm").detach());
        
			$("#frmMain_holdAmounts").autoNumeric('init');
			$("#frmMain_AccountNo").die('keydown');
			$("#frmMain_AccountNo").live('keydown' , function (e) {
				if (e.which == 9)
					$("#btnFindAccount").click();
			});
			$("#frmMain_holdAmounts").focus(function() {
				if(rdb.current == "add") {
					if ($("#frmMain_holdAmounts").val() != '') {
						$("#holdAmount").val($("#frmMain_holdAmounts").val());				
						//$("#frmMain_holdAmounts").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
					}
				}
			});
			$("#frmMain_holdAmounts").blur(function() {
				if(rdb.current == "add") {
					if ($("#frmMain_holdAmounts").val() != '') {
						$("#holdAmount").val($("#frmMain_holdAmounts").val());
						//$("#frmMain_holdAmounts").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
					}
				}
			});
		
			$("#frmMain_giftCode").die('keydown');
			$("#frmMain_giftCode").live('keydown' , function (e) {
				if (e.which == 9)
					$("#btnFindgiftCode").click();
			});
		
			$("#frmMain_Term").die('keydown');
			$("#frmMain_Term").live('keydown' , function (e) {
				if (e.which == 9)
					$("#btnFindTerm").click();
			});
			$("#btnFindAccount").click(function() {
				if(rdb.current == "add") {
					if($("#frmMain_AccountNo").val() != ""){	
						$("#tempValidation_acctSearch").val($("#frmMain_AccountNo").val());
						//alert("nokey");
						$("#tempDlgValidation").click(); 
						labelParams = {};
						labelParams.onClose = function(Account,Product,ProductName,Reason,dateIf){
							if(document.getElementById("errorpas0")){                    
					removesRow3("errorpas0");   
				}
							if(Product == ""){
								//alert("test");
								//$("#supervisor").val("");

								$('Table#AcctVal tbody#body1 tr#acct0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
									+ Reason +'</span></td></tr>');
								$("#btnSubmit").button("disable");	
								//e.preventDefault();
							} else {
							
								$("#frmMain_AccountNo").val(Account);
								$("#frmMain_codProd").val(Product + "-" + ProductName);
								$("#codProds").val(Product);
								$("#namProducts").val(ProductName);
								//$("#frmMain_datesIf").val(dateIf);
								if(document.getElementById("errorpas0")){                    
									removesRow3("errorpas0");	
								}
								$("#frmMain_AccountNo").attr("readonly","readonly");
								$("#btnSubmit").button("enable");
							}
						}
					} else {
						if(document.getElementById("errorpas0")){                    
							removesRow3("errorpas0");   
						}
						$('Table#AcctVal tbody#body1 tr#acct0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
							+"Account No Cannot be Blank"+'</span></td></tr>');
						$("#btnSubmit").button("disable");
					}
				
				} else {
					//if(rdb.current == "Inquiry" || rdb.current == "Delete") {
					dlgParams = {};
					dlgParams.AcctNo = "frmMain_AccountNo";
					dlgParams.ProductCode = "codProds";
					dlgParams.NamProduct = "namProducts";
                
					dlgParams.onClose = function() {
						//alert("test onclose");
					};
					$("#ph-dlg").dialog("option", "position", "center");
					$("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
					//$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
					$("#ph-dlg").dialog("option", "height", "450");
					$("#ph-dlg")
					.html("Please wait...")
					.unbind("dialogopen")
					.bind("dialogopen", function() {
						$("#tempFrmDlgAmortOpeningAcc_AccountNo").val($("#frmMain_AccountNo").val());
						$("#tempFrmDlgAmortOpeningAcc_mode").val($("#modes").val());
						$("#tempFrmDlgAmortOpeningAcc_state").val("ACCOUNT");
						$("#tempFrmDlgAmortOpeningAcc").click();
					})
					.dialog("open");
					//}
				}
				
			});
		
			$("#btnFindgiftCode").click(function() {
				if(!($("#btnFindgiftCode").button('option').disabled != undefined && 
					$("#btnFindgiftCode").button('option', 'disabled'))) {
					$("#giftCodes").val($("#frmMain_giftCode").val());
					//$("#giftCodes").val("");
					
					dlgParams = {};
					dlgParams.giftCode = "giftCodes";
					dlgParams.giftName = "giftName";
					dlgParams.giftPrice = "giftPrice";
					dlgParams.taxPct = "taxPct";
					dlgParams.programID = "programIds";
					dlgParams.programName = "programName";
					dlgParams.status = "status";
					dlgParams.id = "idSequence";
					dlgParams.programDetailId = "idProgram_details";
					dlgParams.type = "type";
					dlgParams.accrualID = "accrualID";
					dlgParams.codProd = "codProds";
					dlgParams.voucherType = "voucherType";
                
					dlgParams.onClose = function() {
						//alert("test onclose");
					};
					$("#ph-dlg").dialog("option", "position", "center");
					$("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
					//$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
					$("#ph-dlg").dialog("option", "height", "450");
					$("#ph-dlg")
					.html("Please wait...")
					.unbind("dialogopen")
					.bind("dialogopen", function() {
						$("#tempFrmDlgAmortOpening_giftCode").val($("#giftCodes").val());
						$("#tempFrmDlgAmortOpening_codProd").val($("#codProds").val());
						$("#tempFrmDlgAmortOpening_mode").val($("#modes").val());
						$("#tempFrmDlgAmortOpening_AccountNo").val($("#frmMain_AccountNo").val());
						$("#tempFrmDlgAmortOpening_state").val("GIFT");
						$("#tempFrmDlgAmortOpening").click();
					})
					.dialog("open");
				}    
			});
			$("#btnFindTerm").click(function() {
				if(!($("#btnFindTerm").button('option').disabled != undefined && 
					$("#btnFindTerm").button('option', 'disabled'))) {

					dlgParams = {};
					dlgParams.term = "frmMain_Term";
					//dlgParams.id = "idSequence";
					dlgParams.onClose = function() {
						//alert("test onclose");
					};
					$("#ph-dlg").dialog("option", "position", "center");
					$("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
					//$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
					$("#ph-dlg").dialog("option", "height", "450");
					$("#ph-dlg")
					.html("Please wait...")
					.unbind("dialogopen")
					.bind("dialogopen", function() {
						$("#tempFrmDlgAmortTerm_giftCode").val($("#giftCodes").val());
						$("#tempFrmDlgAmortTerm_codProd").val($("#codProds").val());
						$("#tempFrmDlgAmortTerm_mode").val($("#modes").val());
						$("#tempFrmDlgAmortTerm_AccountNo").val($("#frmMain_AccountNo").val());
						$("#tempFrmDlgAmortTerm_state").val("TERM");
						$("#tempFrmDlgAmortTerm").click();
					})
					.dialog("open");
				}    
			});
			$("#btnSubmit")
			.unsubscribe("beforeSubmit")
			.subscribe("beforeSubmit", function(event) {
				//$("#frmMain").unbind("submit");
				event.originalEvent.options.submit = false;
				var result = fillValidation();
				if (result) {
					dlgParams = {};
					dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
					dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
					dlgParams.event = event;
					dlgParams.onSubmit = function() {

						//dlgParams.event.originalEvent.options.submit = true;
						$("#btnSubmit").unsubscribe("beforeSubmit").click();
						$("#btnSubmit").button("disable");
						//$("#btnSubmit").attr("disabled","true");
					};
					//if(result == true){
					$("#ph-dlg").dialog("option", "position", "center");
					$("#ph-dlg").dialog("option", "width", "320");
					$("#ph-dlg").dialog("option", "height", "180");
					$("#ph-dlg")
					.html("Please wait...")
					.unbind("dialogopen")
					.bind("dialogopen", function() {
						$("#tempDlgAuth").click();
					})
					.dialog("open");
					/*}else {
					$("#btnSave").unsubscribe("beforeSubmit");
					$("#btnSave").click();
					$("#btnSave").off();
				}*/
				} else {
					return false;
				}
			});
			/* disable right click on form */
			$("form").bind("contextmenu", function(e) {
				e.preventDefault();
			});
		});
</script>
</s:if>