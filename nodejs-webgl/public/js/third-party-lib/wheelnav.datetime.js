/* ==============================================================================================  */
/*                                   wheelnav.datetime.js - v0.1.0                                 */
/* ==============================================================================================  */
/* This is a small javascript library for wheel based date and time.                               */
/* Requires wheelnav.js library (http://wheelnavjs.softwaretailoring.net)                          */
/* ==============================================================================================  */
/* Check http://wheelnavjs.softwaretailoring.net/datetime for samples.                             */
/* Fork https://github.com/softwaretailoring/wheelnav.datetime for contribution.                   */
/* =============================================================================================== */
/* Copyright © 2015 Gábor Berkesi (http://softwaretailoring.net)                                   */
/* Licensed under MIT (https://github.com/softwaretailoring/wheelnav.datetime/blob/master/LICENSE) */
/* =============================================================================================== */
/* Modified by faustineinsun at May 2015                                                           */
/* =============================================================================================== */


wheelnavdatetime = function (divId) {

    this.divId = divId;

    this.wheelTypes = {
        Null: "Null",
        Menu: "Menu",
        Donut: "Donut"
    };

    this.wheelnavDayInWeek = null;
    this.wheelnavHour = null;
    this.wheelnavState = null;

    this.selectionEnable = false;
    this.timeVisible = true;
    this.fixDate = false;
    this.fixTime = false;
    this.slicePathType = this.wheelTypes.Menu;
    this.percentDayInWeekMin = 0;
    this.percentDayInWeekMax = 0.15;
    this.percentHourMin = 0.15;
    this.percentHourMax = 0.3;
    this.percentStateMin = 0.3;
    this.percentStateMax = 0.4;
};

wheelnavdatetime.prototype.initWheelNav = function (wheelnav, minPercent, maxPercent, sliceType, fixed) {

    wheelnav.slicePathCustom = new slicePathCustomization();
    wheelnav.sliceSelectedPathCustom = new slicePathCustomization();
    wheelnav.sliceHoverPathCustom = new slicePathCustomization();

    if (sliceType == this.wheelTypes.Donut) {
        wheelnav.slicePathFunction = slicePath().DonutSlice;
        wheelnav.slicePathCustom.minRadiusPercent = minPercent;
        wheelnav.slicePathCustom.maxRadiusPercent = maxPercent;

        wheelnav.sliceSelectedPathCustom.minRadiusPercent = minPercent;
        wheelnav.sliceSelectedPathCustom.maxRadiusPercent = maxPercent;
    }
    if (sliceType == this.wheelTypes.Menu) {
        wheelnav.slicePathFunction = slicePath().MenuSliceWithoutLine;
        wheelnav.slicePathCustom.titleRadiusPercent = minPercent + ((maxPercent - minPercent) / 2);
        wheelnav.slicePathCustom.menuRadius = ((maxPercent * wheelnav.wheelRadius) - (minPercent * wheelnav.wheelRadius)) / 2;

        wheelnav.sliceSelectedPathCustom.titleRadiusPercent = wheelnav.slicePathCustom.titleRadiusPercent;
        wheelnav.sliceSelectedPathCustom.menuRadius = wheelnav.slicePathCustom.menuRadius * 1.3;

        wheelnav.sliceHoverPathCustom.titleRadiusPercent = wheelnav.slicePathCustom.titleRadiusPercent;
        wheelnav.sliceHoverPathCustom.menuRadius = wheelnav.slicePathCustom.menuRadius * 0.8;
    }
    if (sliceType == this.wheelTypes.Null) {
        wheelnav.slicePathFunction = slicePath().NullSlice;
        wheelnav.slicePathCustom.titleRadiusPercent = minPercent + ((maxPercent - minPercent) / 2);

        wheelnav.sliceSelectedPathCustom.titleRadiusPercent = wheelnav.slicePathCustom.titleRadiusPercent;

        wheelnav.sliceHoverPathCustom.titleRadiusPercent = wheelnav.slicePathCustom.titleRadiusPercent;
    }

    wheelnav.colors = new Array("#EEE");

    wheelnav.slicePathAttr = { fill: "#EEE", stroke: "#111", "stroke-width": 0, cursor: 'pointer', opacity: 0.2 };
    wheelnav.sliceSelectedAttr = { fill: "LightSeaGreen", stroke: "#2f7656", "stroke-width": 0, cursor: 'default', opacity: 0.8 };

    wheelnav.titleAttr = { font: '100 18px sans-serif', fill: "#111", stroke: "none", cursor: 'pointer', opacity: 0.6 };
    wheelnav.titleSelectedAttr = { font: '100 18px sans-serif', fill: "#FFF", cursor: 'default', opacity: 0.8 };

    wheelnav.animateeffect = "linear";
    wheelnav.navItemsEnabled = this.selectionEnable;
    wheelnav.clickModeRotate = !fixed;

    if (!fixed) {
        wheelnav.navAngle = 0;
    }
    else {
        wheelnav.navAngle = -90;
    }

    if (this.selectionEnable) {
        wheelnav.animatetime = 1000;
    }
    else {
        wheelnav.animatetime = 0;
    }
};

wheelnavdatetime.prototype.createWheelNav = function () {
    this.wheelnavDayInWeek = new wheelnav(this.divId);
    this.initWheelNav(this.wheelnavDayInWeek, this.percentDayInWeekMin, this.percentDayInWeekMax, this.slicePathType, this.fixDate);

    this.wheelnavHour = new wheelnav(this.divId + "Hour", this.wheelnavDayInWeek.raphael);
    this.initWheelNav(this.wheelnavHour, this.percentHourMin, this.percentHourMax, this.slicePathType, this.fixDate);

    this.wheelnavState = new wheelnav(this.divId + "State", this.wheelnavDayInWeek.raphael);
    this.initWheelNav(this.wheelnavState, this.percentStateMin, this.percentStateMax, this.slicePathType, this.fixDate);

    this.wheelnavDayInWeek.createWheel(["Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]);
    this.wheelnavHour.createWheel(["0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"]);
    this.wheelnavState.createWheel(["BW", "SCB", "MLN", "SC", "IL", "ELN", "NV", "QC", "WI", "AZ", "CA", "KHL", "ON", "FIF", "WA", "EDH", "PA", "NC"]);
};
