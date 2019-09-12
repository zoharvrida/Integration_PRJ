<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
	<s:url var="ajaxUpdateTitle" action="25301_title" />
	<s:url var="ajaxUpdateButtons" action="25301_buttons" />
	<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
	
	<s:actionmessage id="actionMessage" />
	<s:actionerror name="actionError" />
	
	<%-- Authentication --%>
	<s:form id="frmDlgAuth" action="dlg">
		<s:hidden name="dialog" value="dlgAuth" />
		<s:hidden name="idMenu" value="25301" />
	</s:form>
	<sj:a id="aDlgAuth" formIds="frmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	<%-- Lookup Gift Code --%>
	<s:form id="frmDlgGiftCode" action="dlg">
		<s:hidden name="dialog" />
		<s:hidden name="giftCode" />
		<s:hidden name="mode" />
		<s:token />
	</s:form>
	<sj:a id="aDlgGiftProduct" formIds="frmDlgGiftCode" targets="ph-dlg" cssClass="ui-helper-hidden" />
	
	
	<s:form id="frmMain" name="frmMain" action="25301" method="POST" theme="css_xhtml">
		<fieldset id="fsGift" class="ui-widget-content ui-corner-all" style="border:0px">
			<legend class="ui-widget-header ui-corner-all ui-helper-hidden"><s:text name="label.amortize.fieldset.legend.master" /></legend>
			<table>
				<tbody>
					<tr>
						<td>
							<s:label value="12" cssStyle="visibility:hidden" />
						</td>
						<td>
							<s:label id="lblGiftCode" key="label.amortize.gift.code" />
						</td>
						<td>
							<s:textfield name="compositeId.giftCode" size="30" maxlength="10" cssClass="ui-widget ui-widget-content" 
								onblur="reloadProductAutocompleter();" />
						</td>
						<td>
							<s:label value="12345" cssStyle="visibility:hidden" />
						</td>
						<td>
							<s:label id="lblProductCode" key="label.amortize.gift.product.code" />
						</td>
						<td>
							<s:hidden name="compositeId.productCode" />
							<s:url var="urlProductCode" action="" />
							<sj:autocompleter
								id="productCode"
								name="productCode"
								href="%{urlProductCode}"
								list="modelMap"
								delay="50"
								loadMinimumCount="2"
								formIds="frmEmpty"
								size="50"
								requestType="POST"
								onblur="if($('#productCode').val() == '') {$('#productCode_widget').attr('value', '');}"
								onchange="$('#productCode').val('');"
								onSelectTopics="productSelected"
							/>
						</td>
					</tr>
					
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblGiftName" key="label.amortize.gift.name" />
						</td>
						<td>
							<s:textfield name="giftName" size="37" maxlength="60" cssClass="ui-widget ui-widget-content" />
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblAccrualId" key="label.amortize.gift.accrual.id" />
						</td>
						<td>
							<s:textfield name="idAccrual" size="37" maxlength="40" cssClass="ui-widget ui-widget-content" />
						</td>
					</tr>
	
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblProgramId" key="label.amortize.gift.program.id" />
						</td>
						<td>
							<s:textfield name="programId" size="37" maxlength="15" cssClass="ui-widget ui-widget-content" />
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblProgramName" key="label.amortize.gift.program.name" />
						</td>
						<td>
							<s:textfield name="programName" size="37" maxlength="80" cssClass="ui-widget ui-widget-content" />
						</td>
					</tr>
					
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblType" key="label.amortize.gift.type" />
						</td>
						<td>
							<s:select name="type" list="#{'1':'1 - Advanced', '2':'2 - Arrear'}" cssClass="ui-widget ui-widget-content" />
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblAmortizeMethod" key="label.amortize.gift.amortize.method" />
						</td>
						<td>
							<s:select name="amortizeMethod" list="#{'1':'1 - Straight Line', '2':'2 - EIR'}" cssClass="ui-widget ui-widget-content" />
						</td>
					</tr>
					
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblStatus" key="label.amortize.gift.status" />
						</td>
						<td>
							<s:select name="status" list="#{'1':'1 - Enable', '0':'2 - Disable'}" cssClass="ui-widget ui-widget-content" />
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblTax" key="label.amortize.gift.tax" />
						</td>
						<td>
							<s:textfield name="taxPercent" size="5" maxlength="6" cssStyle="text-align:right" cssClass="ui-widget ui-widget-content" />
						</td>
					</tr>
					
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							<s:label id="lblVoucher">Voucher</s:label>
						</td>
						<td>
							<s:checkbox name="voucher" cssClass="ui-widget ui-widget-content" />
						</td>
						<td colspan="3">
							&nbsp;
						</td>
					</tr>
					
					<tr><td colspan="6">&nbsp;</td></tr>
					
					<tr>
						<td colspan="2" align="center">
							<sj:submit 
								id="btnSubmit" 
								formIds="frmMain" 
								buttonIcon="ui-icon-gear"
								button="true" 
								key="button.save" 
								disabled="true" 
								targets="ph-main"
								onBeforeTopics="btnSubmit_beforeSubmit" 
							/>
							
							<s:hidden name="idMaintainedSpv" />
						</td>
						<td colspan="4" />
					</tr>
					
					<tr><td colspan="6">&nbsp;</td></tr>
				</tbody>
			</table>
			
			<%-- TabbedPanel for Grid --%>
			<s:url var="urlTab" action="25301Tab_none" />
			<sj:tabbedpanel id="tabbedPanel" disabled="true" cssStyle="border:0px; display:none">
				<sj:tab id="tabDetail" href="%{urlTab}" label="Gift Program Detail" />
			</sj:tabbedpanel>
	
			<s:token />
		</fieldset>
		
		<%-- Buttons --%>
		<sj:a id="btnLookupGiftCode" button="true">...</sj:a>
	</s:form>
	
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$("#ph-main").find("div[id^='wwctrl_lbl']").remove(); /* because tag s:label generate double text */
			$("#frmMain > fieldset > legend")
				.css("margin-left", "10px")
				.css("text-align", "center")
				.css("width", function() {
					var width_ = ((parseInt($(this).css("width"), 10) + 25) + "px");
					return width_;
				});
			$("#fsGift").css("height", parseInt($("#ph-main").height()) * 0.95 + "px");
			$('#tabbedPanel ul').attr('style', 'display:none');
			
				
			if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
				$("#rbBS").buttonset("destroy");
				$("#tempTitle").click();
				$("#tempButtons").click();
			} else {
				$("#rbBS").data("rdb").callCurrent();
				if ($("#actionError").length == 0) {
					$("#rbBS").data("rdb").clear(false);
				}
	
			}
			
			/* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */ 
			if (navigator.appName.indexOf("Internet Explorer") > -1) {
				$("*[role='button']", $("#frmMain")).css("position", "static");
			}
			
			$("#frmMain_compositeId_giftCode").parent().append('&nbsp;').append($("#btnLookupGiftCode").detach());
			$("#productCode_widget").addClass("ui-widget-content");
			$("#productCode_widget.ui-autocomplete-input").css("-moz-user-select", "none");
			$("#frmMain_taxPercent").parent().append(' %');
			//$("#frmMain_voucher").switchButton();
			
			$("#frmMain_voucher").bind("change", function(){
				$(this).attr("value", $(this).prop("checked"));
			});
			
			var loadTabPanel = function() {
				$('#tabbedPanel').tabs({
					ajaxOptions :{
						type: 'POST',
						data: {
							'mode': rdb.current,
							'giftCode': $("#frmMain_compositeId_giftCode").val(),
							'productCode': $("#frmMain_compositeId_productCode").val()
						}
					}						
				});
				
				$("#tabbedPanel").tabs({selected : 0});
				$("#tabbedPanel").tabs("url", 0, "25301Tab_execute.action");
				$("#tabbedPanel").tabs("load", 0);
				
				$("#tabbedPanel").show();
			};
			
			$("#btnLookupGiftCode").click(function() {
				if (!(
						($(this).button('option').disabled != undefined) &&
						($(this).button('option', 'disabled'))
						)) {
					dlgParams = {};
					dlgParams.id = "frmMain_compositeId_giftCode";
					dlgParams.desc = "frmMain_giftName";
					dlgParams.type = "frmMain_type";
					dlgParams.status = "frmMain_status";
					dlgParams.progId = "frmMain_programId";
					dlgParams.progDesc = "frmMain_programName";
					dlgParams.idAccrual = "frmMain_idAccrual";
					dlgParams.taxPercent = "frmMain_taxPercent";
					dlgParams.method = "frmMain_amortizeMethod";
					dlgParams.voucher = "frmMain_voucher";
					dlgParams.prodId = "productCode";
					dlgParams.prodDesc = "productCode_widget";
					dlgParams.selected = false;
					
					var $tmp = $("#ph-dlg").dialog("option", "title");
					$("#ph-dlg").dialog("option", "position", "center");
					$("#ph-dlg").dialog("option", "width", ($("body").width() * (3/4)));
					$("#ph-dlg").dialog("option", "height", 450);
					
					$("#ph-dlg")
						.html("Please wait...")
						.unbind("dialogopen")
						.bind("dialogopen", function() {
							$("#frmDlgGiftCode_dialog").val('dlgAmortizeGiftCode');
							$("#frmDlgGiftCode_giftCode").val($("#frmMain_compositeId_giftCode").val());
							$("#frmDlgGiftCode_mode").val(rdb.current == 'add'? '1': '2');
							$("#aDlgGiftProduct").click();
						})
						.unbind("dialogclose")
						.bind("dialogclose", function() {
							$(this).dialog({title: $tmp});
							
							if (dlgParams.selected) {
								$("#frmMain_compositeId_giftCode").attr("readonly", true);
								$("#productCode_widget").attr("readonly", true);
								$("#frmMain_compositeId_productCode").attr("value", $("#productCode").val());
								$("#frmMain_voucher").prop("checked", $("#frmMain_voucher").val() == 'true');
								if (rdb.current != 'inquiry')
									openLockedAmortizeInputs();
								else
									changeViewOfReadOnlyTextElement("frmMain");
								loadTabPanel();
								$("#btnSubmit").button("enable");
							}
						})
						.dialog({
							title: '<s:text name="label.amortize.gift.code" />'
						})
						.dialog("open");
				}
			});
			
			
			$("#productCode")
				.unsubscribe("productSelected")
				.subscribe("productSelected", function(event, data) {
					var ui = event.originalEvent.ui;
					$("#frmMain_compositeId_productCode").attr("value", ui.item.key);
					$("#frmMain_compositeId_giftCode").attr("readonly", true);
					$("#productCode_widget").attr("readonly", true);
					$("#btnSubmit").button("enable");
					
					openLockedAmortizeInputs();
				});
				
			$("#btnSubmit")
				.unsubscribe("btnSubmit_beforeSubmit")
				.subscribe("btnSubmit_beforeSubmit", function(event) {
					event.originalEvent.options.submit = false;
					
					dlgParams = {};
					dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
					dlgParams.onSubmit = function() {
						$("#btnSubmit")
							.unsubscribe("btnSubmit_beforeSubmit")
							.click();
					};
					
					$("#ph-dlg")
						.dialog("option", "position", "center")
						.dialog("option", "width", "320")
						.dialog("option", "height", "180")
						.html("Please wait...")
						.unbind("dialogopen")
						.bind("dialogopen", function() {
							$("#aDlgAuth").click();
						})
						.dialog("open");
				});
			
			function openLockedAmortizeInputs() {
				readOnlyElement("frmMain_giftName", false);
				disableElement("frmMain_status", false);
				readOnlyElement("frmMain_programId", false);
				readOnlyElement("frmMain_programName", false);
				disableElement("frmMain_type", false);
				disableElement("frmMain_amortizeMethod", false);
				readOnlyElement("frmMain_idAccrual", false);
				readOnlyElement("frmMain_taxPercent", false);
				disableElement("frmMain_voucher", false);
				
				changeViewOfReadOnlyTextElement("frmMain");
			}
				
			
			function readOnlyElement(elem, isReadOnly) {
				$("#" + elem).attr("readonly", (isReadOnly == true)? "true": null);
			}
			
			function disableElement(elem, isDisable) {
				$("#" + elem).attr("disabled", (isDisable == true)? "true": null);
			}
				
			/* disable right click on form */
			$("form").bind("contextmenu", function(e) {
				e.preventDefault();
			});
			
			/* disable backspace on all element except writeable input */
			$("#frmMain > *")
				.unbind('keydown')
				.bind('keydown', function(e) {
					var evt = e? e: event;
					var key_ = (evt.charCode)? evt.charCode: evt.keyCode;
					keyPass = false;
					
					if ((key_ == 8) && !(('INPUT'.indexOf(evt.target.nodeName) > -1) && !(evt.target.hasAttribute('readonly')))) {
						evt.preventDefault();
					}
				});
		});
		
		function reloadProductAutocompleter() {
			var options_productCode_widget = {};
			options_productCode_widget.hiddenid = "productCode";
			options_productCode_widget.delay = 50;
			options_productCode_widget.minimum = 2;
			options_productCode_widget.selectBox = false;
			options_productCode_widget.forceValidOption = true;
			options_productCode_widget.onselecttopics = "productSelected";
			options_productCode_widget.list = "modelMap";
		
			options_productCode_widget.jqueryaction = "autocompleter";
			options_productCode_widget.id = "productCode_widget";
			options_productCode_widget.name = "productCode_widget";
			options_productCode_widget.href = "json/25301PickList_list.action";
			options_productCode_widget.hrefparameter = "mode=1&rows=10&page=1&giftCode=" + $("#frmMain_compositeId_giftCode").val();
			options_productCode_widget.formids = "";
			options_productCode_widget.requesttype = "POST";
		  
			jQuery.struts2_jquery_ui.bind(jQuery('#productCode_widget'), options_productCode_widget);
		}
	</script>
</s:if>