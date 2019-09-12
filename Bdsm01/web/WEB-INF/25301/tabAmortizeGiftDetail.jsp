<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>


<s:actionerror name="actionError" />

<script type="text/javascript">
	Number.prototype.formatMoney = function(c, d, t) {
		var n = this;
		c = isNaN(c = Math.abs(c)) ? 2 : c;
		d = d == undefined ? "." : d;
		t = t == undefined ? "," : t;
		s = n < 0 ? "-" : "";
		i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "";
		j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "")
				+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t)
				+ (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};
	
	function customCurrency(cellValue, options, rowObjects) {
		var result = String(cellValue); // cellValue is Numeric
		result = result.replace(',', '.');
		result = parseFloat(result);

		return result.formatMoney(2);
	}

	datePick = function(element) {
		$(element).attr("readonly", true);
		$(element).datepicker({
			dateFormat: 'dd-M-yy',
			minDate: new Date("<s:date name='%{#session.dtBusiness}' format='yyyy' />", 
						"<s:date name='%{#session.dtBusiness}' format='MM' />"-1, "<s:date name='%{#session.dtBusiness}' format='dd' />"),
			defaultDate: new Date("<s:date name='%{#session.dtBusiness}' format='yyyy' />", 
						"<s:date name='%{#session.dtBusiness}' format='MM' />"-1, "<s:date name='%{#session.dtBusiness}' format='dd' />"),
			changeYear: true,
			showAnim: 'slide',
			buttonImage: "calendar.gif",
			buttonImageOnly: true
		});
	};
	
	textMoney = function(element) {
		$(element).autoNumeric('init');
	};
</script>

<div id="detailDiv" class=".ui-widget-header">
	<s:form id="frmDetailDelete" action="25301Grid_doDelete" namespace="/json" method="POST">
		<s:hidden name="id" value="" />
		<s:token />
	</s:form>
	<sj:a id="aDetailDelete" formIds="frmDetailDelete" targets="ph-temp" cssClass="ui-helper-hidden" onSuccessTopics="btnDelete_success" />
	
	
	<s:url var="urlGridChange" action="25301Grid_doChange" namespace="/json" />
	<s:url var="urlGridList" action="25301Grid_list" namespace="/json" escapeAmp="false" />
	<sjg:grid 
		id="detailGridTable" 
		caption="Gift Term"
		dataType="local" 
		href="%{urlGridList}" 
		requestType="POST" 
		pager="true"
		pagerPosition="right" 
		gridModel="gridTemplate" 
		rowNum="15"
		rownumbers="true"
		shrinkToFit="true" 
		width="800"
		altRows="true" 
		navigator="false" 
		editurl="%{urlGridChange}"
		onGridCompleteTopics="detailGridCompleted" 
		onSelectRowTopics="rowSelected"
	>
		<sjg:gridColumn name="id" title="ID" hidden="true" />
		<sjg:gridColumn name="giftCode" title="Gift Code" editable="true" hidden="true" />
		<sjg:gridColumn name="productCode" title="Product Code" editable="true" hidden="true" />
		<sjg:gridColumn name="effectiveDate" title="Effective Date" width="8" align="center" formatter="date" 
				formatoptions="{newformat : 'd-M-Y', srcformat : 'Y-m-d H:i:s'}" sortable="false" 
				editable="true" editrules="{required:true}" editoptions="{size:12, maxlength:10, dataInit:datePick}" />
		<sjg:gridColumn name="term" title="Term" width="5" align="center" editable="true" editrules="{number:true, required:true, minValue: 1, maxValue: 300}" 
				sortable="false" />
		<sjg:gridColumn name="giftPrice" title="Gift Price" width="10" align="right" editable="true" editoptions="{dataInit:textMoney}" editrules="{required:true}" 
				formatter="customCurrency" sortable="false" />
		<sjg:gridColumn name="minimumHold" title="Minimum Hold Amount" width="15" align="right" editable="true" editoptions="{dataInit:textMoney}" 
				editrules="{required:true}" formatter="customCurrency" sortable="false" />
		<sjg:gridColumn name="maximumHold" title="Maximum Hold Amount" width="15" align="right" editable="true" editoptions="{dataInit:textMoney}" 
				editrules="{required:true}" formatter="customCurrency" sortable="false" />
	</sjg:grid>
	
	<s:set var="mode_" scope="request">${param.mode}</s:set>
	<s:if test="%{#request.mode_.equals('edit')}">
		<div id="divButtons" align="right">
			<table>
				<tr align="right">
					<td>
						<sj:submit
							id="btnAdd"
							formIds="frmDetailDelete" 
							buttonIcon="ui-icon-gear" 
							button="true"
							key="button.add" 
							targets="ph-temp"
							onBeforeTopics="btnAdd_beforeSubmit"
						/>
					</td>
					<td>
						<sj:submit 
							id="btnDelete"
							formIds="frmDetailDelete" 
							buttonIcon="ui-icon-gear" 
							button="true"
							key="button.delete"
							targets="ph-temp"
							onBeforeTopics="btnDelete_beforeSubmit"
						/>
					</td>
				</tr>
			</table>
		</div>
	</s:if>
</div>

<script type="text/javascript">
	jQuery(document).ready(function() {
		/* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */ 
		if (navigator.appName.indexOf("Internet Explorer") > -1) {
			$("*[role='button']", $("#divButtons")).css("position", "static");
		}
		
		$("#detailGridTable").unsubscribe("detailGridCompleted");
		$("#detailGridTable").subscribe("detailGridCompleted", function(event, data) {
			$("#divButtons").width($("#detailGridTable").width());
			
			if ($('#detailGridTable').jqGrid('getGridParam', 'records') == 0) {
				$("#detailGridTable_pager_left").append("<span id='note' style='color:red'>No records found</span>");
				$("#btnDelete").hide();
			}
			else {
				$("th.ui-th-column div").css({"white-space": "normal", "height" : "auto"});
				$("td[role='gridcell']").css("vertical-align", "middle");
				//$("#gview_aprvGridTable div.ui-jqgrid-bdiv").css("height", $('#aprvGridTable').jqGrid('getGridParam', 'records') * 23);
				$("#detailGridTable_pager_left #note").remove();
				$("#btnDelete").show();
			}
		});
		
		$("#detailGridTable").unsubscribe("rowSelected");
		$("#detailGridTable").subscribe("rowSelected",
			function(event, data) {
				$("#frmDetailDelete_id").val($(data).jqGrid("getCell", event.originalEvent.id, "id"));
			}
		);
		
		$("#detailGridTable").jqGrid('setGridParam', {
			datatype: 'json',
			postData: {
				'giftCode': function() {return '<s:property value="%{#parameters.giftCode}" />';},
				'productCode' : function() {return '<s:property value="%{#parameters.productCode}" />';}
			}
		});
		$("#detailGridTable").trigger('reloadGrid');
		
		
		$("#btnAdd")
			.unsubscribe("btnAdd_beforeSubmit")
			.subscribe("btnAdd_beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				$("#detailGridTable").jqGrid('editGridRow', 'new', 
					{
						editData: {
							'giftCode':'<s:property value="%{#parameters.giftCode}" />', 
							'productCode': '<s:property value="%{#parameters.productCode}" />'
						},
						beforeSubmit: function(postData, formId) {
							var min = parseInt($("#minimumHold", formId).autoNumeric('get'));
							var max = parseInt($("#maximumHold", formId).autoNumeric('get'));
							if (min > max)
								return [false, "maximum Hold Amount must be greater or equal than minimum Hold Amount"];
							else
								return [true, ""];
						},
						afterSubmit: function(response, postData) {
							var responseObj = jQuery.parseJSON(response.responseText);
							var errMsg = responseObj.errorMessage;
							
							if ((errMsg != null) && (errMsg != '')) {
								return [false, errMsg, 0];
							}
							else
								return [true, "", 0];
						},
						closeAfterAdd:true, 
						reloadAfterSubmit:true
					}
				);
			});
		
		$("#btnDelete")
			.unsubscribe("btnDelete_beforeSubmit")
			.subscribe("btnDelete_beforeSubmit", function(event) {
				event.originalEvent.options.submit = false;
				if ($("#frmDetailDelete_id").val() == '') {
					showMessage("Gift Term", "Please select the row first!!!");
				}
				else {
					confirmYesOrNo("Gift Term", "Are you sure want to delete?", function() { $("#aDetailDelete").click(); });
				}
			});
			
		$("#btnDelete")
			.unsubscribe("btnDelete_success")
			.subscribe("btnDelete_success", function(event, data) {
				var errMsg = event.originalEvent.data.errorMessage;
				if (errMsg == null || errMsg == '')
					$("#detailGridTable").trigger('reloadGrid');
				else {
					showMessage("Gift Term", errMsg);
				}
			});
			
		function showMessage(title, message) {
			var $temp = $("#ph-error").html();
			$("#ph-error")
				.html(message)
				.dialog({
					autoOpen: false,
					modal: true,
					title: title,
					buttons: {
						OK: function() {$(this).dialog("close");} 
					},
					show: "slide",
					hide: "puff",
					width: "auto",
					height: "auto",
					position: "center",
					resizable: false,
					close: function() {
						$(this).html($temp);
						$(this).dialog("destroy");
					}
				})
				.dialog("open");
		}
		
		function confirmYesOrNo(title, message, funcWhenYes) {
			var $temp = $("#ph-error").html();
			
			$("#ph-error")
				.html(message)
				.dialog({
					autoOpen: false,
					modal: true,
					title: title,
					buttons: {
						Yes: function() {
									funcWhenYes(); 
									$(this).dialog("close");
								},
						No: function() {$(this).dialog("close");} 
					},
					width: "auto",
					height: "auto",
					position: "center",
					resizable: false,
					close: function() {
						$(this).html($temp);
						$(this).dialog("destroy");
					}
				})
				.dialog("open");
		}
			
	});
</script>
