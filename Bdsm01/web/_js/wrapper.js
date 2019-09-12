function checkAplhanumeric(check){
    var s= check;
    var result = s.split("\\");
    
    var co= (result.length)-1;
    var newstring=result[co];

    var regex = new RegExp("^[a-zA-Z0-9\-_\.]*$");
    if (!newstring.match(regex))
        return false;
    else
        return true;
}

function showError(content, title, phId) {
    if (phId == null)
        phId = "ph-error";
    if (title == null)
        title = "Error";
    jQuery("#" + phId).dialog("option", "title", title);
    var s = "<div class='ui-widget'><div class='ui-state-error ui-corner-all' style='padding:0.7em'><p>";
    s += "<span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>";
    for (var i = 0; i < content.length; i++) {
        s += content[i];
        s += "<br/>";
    }
    s += "</p></div></div>";
    jQuery("#" + phId).html(s);
    jQuery("#" + phId).dialog("open");
}

function getCookie(c_name) {
    var i, x, y, ARRcookies = document.cookie.split(";");
    for (i = 0; i < ARRcookies.length; i++) {
        x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
        y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
        x = x.replace(/^\s+|\s+$/g, "");
        if (x == c_name) {
            return unescape(y);
        }
    }
    return null;
}

function setCookie(c_name, value, exdays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var c_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
    document.cookie = c_name + "=" + c_value;
}

function changeAction(frm, action, mode) {
    var s = "^.*" + action + ".*\\.action*";
    var regex = new RegExp(s, "g");
    var $form = $("#" + frm);
    if (($form != null) && ($form != undefined) && ($form.attr("action") != undefined))
        $form.attr("action", $form.attr("action").replace(regex, action + mode + ".action"));
}

function RDB() {
    this.listDisabled = {};
    this.listReadonly = {};
    this.listDisableButton = {};
    this.current = null;
    this.pf_inquiry = null;
    this.pf_add = null;
    this.pf_edit = null;
    this.pf_delete = null;
    this.pf_approve = null;
    this.f_inquiry = null;
    this.f_add = null;
    this.f_edit = null;
    this.f_delete = null;
    this.f_approve = null;
    this.pf_clear = null;

    this.resetDisabled = function() {
        this.listDisabled = {};
    };

    this.resetReadonly = function() {
        this.listReadonly = {};
    };

    this.resetDisableButton = function() {
        this.listDisableButton = {};
    };

    this.doDisabled = function(s) {
        for (var m in this.listDisabled[s]) {
            if (this.listDisabled[s][m]) {
                $("#" + m).attr("disabled", "true");
            } else {
                $("#" + m).attr("disabled", null);
            }
        }
    };

    this.doReadonly = function(s) {
        for (var m in this.listReadonly[s]) {
            if (this.listReadonly[s][m]) {
                $("#" + m).attr("readonly", "true");
            } else {
                $("#" + m).attr("readonly", null);
            }
        }
    };

    this.doDisableButton = function(s) {
        for (var m in this.listDisableButton[s]) {
            if (this.listDisableButton[s][m]) {
                $("#" + m).button("disable");
            } else {
                $("#" + m).button("enable");
            }
        }
    };

    this.setDisabled = function(f, modes) {
        for (var i = 0; i < 5; i++) {
            var m = modes.charAt(i);
            var s = "inquiry";
            switch (i) {
                case 1:
                    s = "add";
                    break;
                case 2:
                    s = "edit";
                    break;
                case 3:
                    s = "delete";
                    break;
                case 4:
                    s = "approve";
                    break;
            }
            if (!this.listDisabled[s])
                this.listDisabled[s] = {};
            if (m == '1') {
                this.listDisabled[s][f] = true;
            } else {
                this.listDisabled[s][f] = false;
            }
        }
    };

    this.setReadonly = function(f, modes) {
        for (var i = 0; i < 5; i++) {
            var m = modes.charAt(i);
            var s = "inquiry";
            switch (i) {
                case 1:
                    s = "add";
                    break;
                case 2:
                    s = "edit";
                    break;
                case 3:
                    s = "delete";
                    break;
                case 4:
                    s = "approve";
                    break;
            }
            if (!this.listReadonly[s])
                this.listReadonly[s] = {};
            if (m == '1') {
                this.listReadonly[s][f] = true;
            } else {
                this.listReadonly[s][f] = false;
            }
        }
    };

    this.setDisableButton = function(f, modes) {
        for (var i = 0; i < 5; i++) {
            var m = modes.charAt(i);
            var s = "inquiry";
            switch (i) {
                case 1:
                    s = "add";
                    break;
                case 2:
                    s = "edit";
                    break;
                case 3:
                    s = "delete";
                    break;
                case 4:
                    s = "approve";
                    break;
            }
            if (!this.listDisableButton[s])
                this.listDisableButton[s] = {};
            if (m == '1') {
                this.listDisableButton[s][f] = true;
            } else {
                this.listDisableButton[s][f] = false;
            }
        }
    };

    this.setOnChange = function(inquiry, add, edit, del, clr, apr) {
        $("#rbBS").data("rdb", this);
        $("#rbBSInquiry").data("rdb", this);
        $("#rbBSAdd").data("rdb", this);
        $("#rbBSEdit").data("rdb", this);
        $("#rbBSDelete").data("rdb", this);
        $("#rbBSClear").data("rdb", this);
        $("#rbBSApprove").data("rdb", this);
        this.pf_clear = function(removeMsg) {
            if (clr !== undefined)
                clr(removeMsg);
            $("#frmGo_idMenu").val("");
            $("#frmGo_noCif").val("");
            $("#frmGo_goAction").val("");
        };

        $("#rbBSInquiry")
                .button("enable")
                .unbind("change")
                .bind("change", function() {
            $(this).data("rdb").pf_inquiry = inquiry;
            $(this).data("rdb").current = "inquiry";
            //alert($(this).data("rdb").current);
            $(this).data("rdb").doDisabled("inquiry");
            $(this).data("rdb").doReadonly("inquiry");
            $(this).data("rdb").doDisableButton("inquiry");
            if ($(this).data("rdb").pf_inquiry != undefined)
                $(this).data("rdb").pf_inquiry();
            if ($(this).data("rdb").pf_clear != undefined)
                $(this).data("rdb").pf_clear(true);
        });

        $("#rbBSAdd")
                .button("enable")
                .unbind("change")
                .bind("change", function() {
            $(this).data("rdb").pf_add = add;
            $(this).data("rdb").current = "add";
            //alert($(this).data("rdb").current);
            $(this).data("rdb").doDisabled("add");
            $(this).data("rdb").doReadonly("add");
            $(this).data("rdb").doDisableButton("add");
            if ($(this).data("rdb").pf_add != undefined)
                $(this).data("rdb").pf_add();
            if ($(this).data("rdb").pf_clear != undefined)
                $(this).data("rdb").pf_clear(true);
        });
        $("#rbBSEdit")
                .button("enable")
                .unbind("change")
                .bind("change", function() {
            $(this).data("rdb").pf_edit = edit;
            $(this).data("rdb").current = "edit";
            //alert($(this).data("rdb").current);
            $(this).data("rdb").doDisabled("edit");
            $(this).data("rdb").doReadonly("edit");
            $(this).data("rdb").doDisableButton("edit");
            if ($(this).data("rdb").pf_edit != undefined)
                $(this).data("rdb").pf_edit();
            if ($(this).data("rdb").pf_clear != undefined)
                $(this).data("rdb").pf_clear(true);
        });

        $("#rbBSDelete")
                .button("enable")
                .unbind("change")
                .bind("change", function() {
            $(this).data("rdb").pf_delete = del;
            $(this).data("rdb").current = "delete";
            //alert($(this).data("rdb").current);
            $(this).data("rdb").doDisabled("delete");
            $(this).data("rdb").doReadonly("delete");
            $(this).data("rdb").doDisableButton("delete");
            if ($(this).data("rdb").pf_delete != undefined)
                $(this).data("rdb").pf_delete();
            if ($(this).data("rdb").pf_clear != undefined)
                $(this).data("rdb").pf_clear(true);
        });

        $("#rbBSClear")
                .button("enable")
                .unbind("change")
                .bind("change", function() {
            if ($(this).data("rdb").pf_clear != undefined)
                $(this).data("rdb").pf_clear(true);
            $(this).data("rdb").callCurrent();
            if ($(this).data("rdb").current == "inquiry") {
                $("#rbBSInquiry").change().click();
                $("#rbBSInquiry").button("refresh");
            } else if ($(this).data("rdb").current == "add") {
                $("#rbBSAdd").change().click();
                $("#rbBSAdd").button("refresh");
            } else if ($(this).data("rdb").current == "edit") {
                $("#rbBSEdit").change().click();
                $("#rbBSEdit").button("refresh");
            } else if ($(this).data("rdb").current == "delete") {
                $("#rbBSDelete").change().click();
                $("#rbBSDelete").button("refresh");
            } else if ($(this).data("rdb").current == "approve") {
                $("#rbBSApprove").change().click();
                $("#rbBSApprove").button("refresh");
            }
        });

        $("#rbBSApprove")
                .button("enable")
                .unbind("change")
                .bind("change", function() {
            $(this).data("rdb").pf_approve = apr;
            $(this).data("rdb").current = "approve";
            //alert($(this).data("rdb").current);
            $(this).data("rdb").doDisabled("approve");
            $(this).data("rdb").doReadonly("approve");
            $(this).data("rdb").doDisableButton("approve");
            if ($(this).data("rdb").pf_approve != undefined)
                $(this).data("rdb").pf_approve();
            if ($(this).data("rdb").pf_clear != undefined)
                $(this).data("rdb").pf_clear(true);
        });
    };

    this.f_inquiry = function() {
        //alert("begin inquiry");
        this.doDisabled("inquiry");
        this.doReadonly("inquiry");
        this.doDisableButton("inquiry");
        if (this.pf_inquiry != undefined)
            this.pf_inquiry();
        //alert("end inquiry");
    };
    this.f_add = function() {
        //alert("begin add");
        this.doDisabled("add");
        this.doReadonly("add");
        this.doDisableButton("add");
        if (this.pf_add != undefined)
            this.pf_add();
        //alert("end add");
    };
    this.f_edit = function() {
        //alert("begin edit");
        this.doDisabled("edit");
        this.doReadonly("edit");
        this.doDisableButton("edit");
        if (this.pf_edit != undefined)
            this.pf_edit();
        //alert("end edit");
    };
    this.f_delete = function() {
        //alert("begin delete");
        this.doDisabled("delete");
        this.doReadonly("delete");
        this.doDisableButton("delete");
        if (this.pf_delete != undefined)
            this.pf_delete();
        //alert("end delete");
    };

    this.f_approve = function() {
        this.doDisabled("approve");
        this.doReadonly("approve");
        this.doDisableButton("approve");
        if (this.pf_approve != undefined)
            this.pf_approve();
    };

    this.callCurrent = function() {
        //alert("current:" + this.current);
        if (this.current == null)
            return;

        if (this.current == "inquiry") {
            if (this.f_inquiry != undefined) {
                //alert("f_inquiry");
                this.f_inquiry();
            }
        } else if (this.current == "add") {
            if (this.f_add != undefined) {
                //alert("f_add");
                this.f_add();
            }
        } else if (this.current == "edit") {
            if (this.f_edit != undefined) {
                //alert("f_edit");
                this.f_edit();
            }
        } else if (this.current == "delete") {
            if (this.f_delete != undefined) {
                //alert("f_delete");
                this.f_delete();
            }
        } else if (this.current == "approve") {
            if (this.f_approve() != undefined) {
                //alert("f_delete");
                this.f_approve()();
            }
        }
    };

    this.clear = function(removeMsg) {
        if (this.pf_clear != undefined)
            this.pf_clear(removeMsg);
    };

    this.setMenuAccess = function(flagMenuAccess, flagGlobalAccess) {
        var flgI = flagGlobalAccess.substr(0, 1);
        var flgA = flagGlobalAccess.substr(1, 1);
        var flgE = flagGlobalAccess.substr(2, 1);
        var flgD = flagGlobalAccess.substr(3, 1);
        var flgV = flagGlobalAccess.substr(4, 1); //flagV is approve guys...

        if (flgI == '0') {
            $("#rbBSInquiry").button("disable");
        } else if (flgI == '1') {
            $("#rbBSInquiry").button("enable");
        }

        if (flgA == '0') {
            $("#rbBSAdd").button("disable");
        } else if (flgA == '1') {
            $("#rbBSAdd").button("enable");
        }

        if (flgE == '0') {
            $("#rbBSEdit").button("disable");
        } else if (flgE == '1') {
            $("#rbBSEdit").button("enable");
        }

        if (flgD == '0') {
            $("#rbBSDelete").button("disable");
        } else if (flgD == '1') {
            $("#rbBSDelete").button("enable");
        }

        if (flgV == '0') {
            $("#rbBSApprove").button("disable");
        } else if (flgV == '1') {
            $("#rbBSApprove").button("enable");
        }

        $("#rbBSClear").button("enable");

        flgI = flagMenuAccess.substr(0, 1);
        flgA = flagMenuAccess.substr(1, 1);
        flgE = flagMenuAccess.substr(2, 1);
        flgD = flagMenuAccess.substr(3, 1);
        flgV = flagMenuAccess.substr(4, 1); //flagV is approve guys...
        var buttonClicked = false;

        if (flgI == '0') {
            $("#rbBSInquiry").button("disable");
        } else if (flgI == '1') {
            if (($("#rbBSInquiry").length == 1) && ($("#rbBSInquiry").attr("disabled") === undefined)) {
                $("#rbBSInquiry").button("enable");
                if (!buttonClicked) {
                    this.current = "inquiry";
                    this.callCurrent();
                    $("#rbBSInquiry").change().click();
                    $("#rbBSInquiry").button("refresh");
                    buttonClicked = true;
                }
            }
        }

        if (flgA == '0') {
            $("#rbBSAdd").button("disable");
        } else if (flgA == '1') {
            if (($("#rbBSAdd").length == 1) && ($("#rbBSAdd").attr("disabled") === undefined)) {
                $("#rbBSAdd").button("enable");
                if (!buttonClicked) {
                    this.current = "add";
                    this.callCurrent();
                    $("#rbBSAdd").change().click();
                    $("#rbBSAdd").button("refresh");
                    buttonClicked = true;
                }
            }
        }

        if (flgE == '0') {
            $("#rbBSEdit").button("disable");
        } else if (flgE == '1') {
            if (($("#rbBSEdit").length == 1) && ($("#rbBSEdit").attr("disabled") === undefined)) {
                $("#rbBSEdit").button("enable");
                if (!buttonClicked) {
                    this.current = "edit";
                    this.callCurrent();
                    $("#rbBSEdit").change().click();
                    $("#rbBSEdit").button("refresh");
                    buttonClicked = true;
                }
            }
        }

        if (flgD == '0') {
            $("#rbBSDelete").button("disable");
        } else if (flgD == '1') {
            if (($("#rbBSDelete").length == 1) && ($("#rbBSDelete").attr("disabled") === undefined)) {
                $("#rbBSDelete").button("enable");
                if (!buttonClicked) {
                    this.current = "delete";
                    this.callCurrent();
                    $("#rbBSDelete").change().click();
                    $("#rbBSDelete").button("refresh");
                    buttonClicked = true;
                }
            }
        }

        if (flgV == '0') {
            $("#rbBSApprove").button("disable");
        } else if (flgV == '1') {
            if (($("#rbBSApprove").length == 1) && ($("#rbBSApprove").attr("disabled") === undefined)) {
                $("#rbBSApprove").button("enable");
                if (!buttonClicked) {
                    this.current = "approve";
                    this.callCurrent();
                    $("#rbBSApprove").change().click();
                    $("#rbBSApprove").button("refresh");
                    buttonClicked = true;
                }
            }
        }

    };

    return this;
}

var rdb = new RDB();

/*
function debugObject(object) {
    var s = "{\n";
    for (var key in object) {
        s += key + "=" + object[key] + ";\n";
    }
    s += "}\n";
    return s;
}
*/

(function($) {
    $.widget("bdsm.combobox", {
        options: {
            size: 10
        },
        _create: function() {
            var self = this;
            var mainid = this.element.attr("id");
            var span = $("<span>")
                    .addClass("bdsm-combobox")
                    .attr("id", this.element.attr("id") + "_combobox")
                    .insertAfter(this.element);
            var select = this.element.hide(),
                    selected = select.children(":selected"),
                    value = selected.val() ? selected.text() : "";
            var input = $("<input />")
                    //.insertAfter(select)
                    .attr("size", this.options.size)
                    .appendTo(span)
                    .val(value)
                    .autocomplete({
                        delay: 0,
                        minLength: 0,
                        source: function(request, response) {
                            var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
                            response(select.children("option").map(function() {
                                var text = $(this).text();
                                if (this.value && (!request.term || matcher.test(text)))
                                    return {
                                        label: text.replace(
                                                new RegExp(
                                                        "(?![^&;]+;)(?!<[^<>]*)(" +
                                                        $.ui.autocomplete.escapeRegex(request.term) +
                                                        ")(?![^<>]*>)(?![^&;]+;)", "gi"),
                                                "<strong>$1</strong>"),
                                        value: text,
                                        option: this
                                    };
                            }));
                        },
                        select: function(event, ui) {
                            ui.item.option.selected = true;
                            $(self.element).trigger("change");
                        },
                        change: function(event, ui) {
                            if (!ui.item) {
                                var matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex($(this).val()) + "$", "i"),
                                        valid = false;
                                select.children("option").each(function() {
                                    if (this.value.match(matcher)) {
                                        this.selected = valid = true;
                                        $(self.element).trigger("change");
                                        return false;
                                    }
                                });
                                if (!valid) {
                                    // remove invalid value, as it didn't match anything
                                    $(this).val("");
                                    select.val("");
                                    $(self.element).trigger("change");
                                    return false;
                                }
                            }
                        }
                    })
                    .addClass("ui-widget ui-widget-content");

            input.data("autocomplete")._renderItem = function(ul, item) {
                ul.attr("id", mainid + "_ul");
                return $("<li></li>")
                        .data("item.autocomplete", item)
                        .append("<a>" + item.label + "</a>")
                        .appendTo(ul);
            };

            $("<a>")
                    .attr("onclick", "return false;")
                    .attr("tabIndex", -1)
                    .attr("title", "Show All Items")
                    .insertAfter(input)
                    .button({
                        icons: {
                            primary: "ui-icon-triangle-1-s"
                        },
                        text: false
                    })
                    .removeClass("ui-corner-all")
                    .addClass("bdsm-combobox-toggle ui-corner-right ui-button-icon")
                    .click(function() {
                        // close if already visible
                        if (input.autocomplete("widget").is(":visible")) {
                            input.autocomplete("close");
                            return;
                        }
                        // pass empty string as value to search for, displaying all results
                        input.autocomplete("search", "");
                        input.focus();
                    });
        }
    });


})(jQuery);