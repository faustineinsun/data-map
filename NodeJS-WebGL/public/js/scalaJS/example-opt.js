(function(){'use strict';function aa(a){return function(){return this[a]}}function g(a){return function(){return a}}var h,k="object"===typeof __ScalaJSEnv&&__ScalaJSEnv?__ScalaJSEnv:{},p="object"===typeof k.global&&k.global?k.global:"object"===typeof global&&global&&global.Object===Object?global:this;k.global=p;var q="object"===typeof k.exportsNamespace&&k.exportsNamespace?k.exportsNamespace:p;k.exportsNamespace=q;p.Object.freeze(k);var ba=0;function ca(a){var b,c;for(c in a)b=c;return b}
function fa(a,b,c){var d=new a.xb(b[c]);if(c<b.length-1){a=a.la;c+=1;for(var e=d.G,f=0;f<e.length;f++)e[f]=fa(a,b,c)}return d}function ga(a){return void 0===a?"undefined":a.toString()}
function ha(a){switch(typeof a){case "string":return s(ia);case "number":var b=a|0;return b===a?b<<24>>24===b&&1/b!==1/-0?s(ja):b<<16>>16===b&&1/b!==1/-0?s(ka):s(la):a!==a||ma(a)===a?s(na):s(oa);case "boolean":return s(pa);case "undefined":return s(qa);default:if(null===a)throw(new ra).e();return sa(a)?s(ta):a&&a.a?s(a.a):null}}
function ua(a){switch(typeof a){case "string":return va(t(),a);case "number":return wa(xa(),a);case "boolean":return a?1231:1237;case "undefined":return 0;default:return a&&a.a||null===a?a.r():42}}function ya(a){return null===a?w().q:a}this.__ScalaJSExportsNamespace=q;
function x(a,b,c,d,e,f,m){var l=ca(a);f=f||function(a){return!!(a&&a.a&&a.a.y[l])};m=m||function(a,b){return!!(a&&a.a&&a.a.ib===b&&a.a.hb.y[l])};this.xb=void 0;this.ob=e;this.y=d;this.Xb=this.la=null;this.wb="L"+c+";";this.ja=this.ka=void 0;this.Hb=m;this.name=c;this.isPrimitive=!1;this.isInterface=b;this.isArrayClass=!1;this.isInstance=f}
function za(a){function b(a){if("number"===typeof a){this.G=Array(a);for(var b=0;b<a;b++)this.G[b]=c}else this.G=a}var c=a.Xb;"longZero"==c&&(c=w().q);b.prototype=new y;b.prototype.a=this;var d="["+a.wb,e=a.hb||a,f=(a.ib||0)+1;this.xb=b;this.ob=Aa;this.y={d:1};this.la=a;this.hb=e;this.ib=f;this.Xb=null;this.wb=d;this.Hb=this.ja=this.ka=void 0;this.name=d;this.isInterface=this.isPrimitive=!1;this.isArrayClass=!0;this.isInstance=function(a){return e.Hb(a,f)}}
function s(a){if(!a.ka){var b=new Ba;b.ma=a;a.ka=b}return a.ka}function Ca(a){a.ja||(a.ja=new za(a));return a.ja}x.prototype.getFakeInstance=function(){return this===ia?"some string":this===pa?!1:this===ja||this===ka||this===la||this===na||this===oa?0:this===ta?w().q:this===qa?void 0:{a:this}};x.prototype.getSuperclass=function(){return this.ob?s(this.ob):null};x.prototype.getComponentType=function(){return this.la?s(this.la):null};
x.prototype.newArrayOfThisClass=function(a){for(var b=this,c=0;c<a.length;c++)b=Ca(b);return fa(b,a,0)};za.prototype=x.prototype;var z=p.Math.imul||function(a,b){var c=a&65535,d=b&65535;return c*d+((a>>>16&65535)*d+c*(b>>>16&65535)<<16>>>0)|0},ma=p.Math.fround||function(a){return+a};function A(){}function y(){}y.prototype=A.prototype;A.prototype.e=function(){return this};A.prototype.m=function(){var a=B(ha(this)),b=(+(this.r()>>>0)).toString(16);return a+"@"+b};A.prototype.r=function(){var a;this&&this.a?(a=this.$idHashCode$0,void 0===a&&(this.$idHashCode$0=ba=a=ba+1|0)):a=null===this?0:ua(this);return a};A.prototype.toString=function(){return this.m()};
var Aa=new x({d:0},!1,"java.lang.Object",{d:1},void 0,function(a){return null!==a},function(a,b){var c=a&&a.a;if(c){var d=c.ib||0;return!(d<b)&&(d>b||!c.hb.isPrimitive)}return!1});A.prototype.a=Aa;function C(){}C.prototype=new y;
C.prototype.nb=function(a){var b=a.getContext("2d");a.width=a.parentElement.clientWidth|0;a.height=400;b.font="50px sans-serif";b.textAlign="center";b.textBaseline="middle";var c=Da((a.height|0)/2),d=Da(0),e=(new D).j(0),f=(new D).j(-50),m=Ea().W().X();p.setInterval(function(a,b,c,d,f,e,m,L,M,ea){return function(){Fa();b.clearRect(0,0,a.width|0,a.height|0);0<L.f?(e.f=(a.height|0)/2|0,m.f=0,M.f=-50,ea.k=(new E).e(),ea.o=ea.k,ea.l=0,L.f=-1+L.f|0,b.fillStyle="darkred",b.fillText("Game Over",(a.width|
0)/2|0,(a.height|0)/2|0)):Ga(a,b,c,d,f,e,m,L,M,ea)}}(a,b,200,50,0.1,c,d,e,f,m),20);a.onclick=function(a){return function(){a.f=-5+a.f}}(d)};C.prototype.db=function(a){this.nb(a)};
function Ga(a,b,c,d,e,f,m,l,n,u){n.f=2+n.f|0;if(0<=n.f&&0===n.f%c){var r;Ha||(Ha=(new Ia).e());r=Ha;var v=(a.height|0)-z(2,d)|0;r=[Ja(r.A,v)+d|0];for(var v=0,da=r.length|0;v<da;)Ka(u,r[v]),v=1+v|0}7<u.l&&(La(u),n.f=n.f-c|0);f.f+=m.f;m.f+=e;b.fillStyle="darkblue";Ea();Ma(Na(u),F(function(a){return null!==a})).z(F(function(a,b,c,d,f,e,m){return function(l){if(null!==l){var n=l.ha|0;l=(z(l.ia|0,c)-m.f|0)+(a.width|0)|0;b.fillRect(l,0,5,n-d|0);b.fillRect(l,n+d|0,5,((a.height|0)-n|0)-d|0);l=l-((a.width|
0)/2|0)|0;5>(0>l?-l|0:l)?(n-=f.f,n=(0>n?-n:n)>d):n=!1;n&&(e.f=50)}else throw(new Oa).lb(l);}}(a,b,c,d,f,l,n)));b.fillStyle="darkgreen";b.fillRect(-5+((a.width|0)/2|0)|0,-5+f.f,10,10);if(0>f.f||f.f>(a.height|0))l.f=50}C.prototype.main=function(a){return this.db(a)};C.prototype.a=new x({Zb:0},!1,"canvasapp.FlappyLine$",{Zb:1,d:1});var Pa=void 0;function Fa(){Pa||(Pa=(new C).e());return Pa}q.canvasapp=q.canvasapp||{};q.canvasapp.FlappyLine=Fa;function G(){}G.prototype=new y;
G.prototype.nb=function(a){var b=a.getContext("2d");a.width=a.parentElement.clientWidth|0;a.height=a.parentElement.clientHeight|0;b.fillStyle="#f8f8f8";b.fillRect(0,0,a.width|0,a.height|0);b.fillStyle="black";var c=Qa(!1);a.onmousedown=function(a){return function(){a.f=!0}}(c);a.onmouseup=function(a){return function(){a.f=!1}}(c);a.onmousemove=function(a,b,c){return function(m){var l=a.getBoundingClientRect();c.f&&b.fillRect(+m.clientX-+l.left,+m.clientY-+l.top,10,10)}}(a,b,c)};G.prototype.db=function(a){this.nb(a)};
G.prototype.main=function(a){return this.db(a)};G.prototype.a=new x({$b:0},!1,"example.ScalaJSExample$",{$b:1,d:1});var Ra=void 0;q.example=q.example||{};q.example.ScalaJSExample=function(){Ra||(Ra=(new G).e());return Ra};function Ba(){this.ma=null}Ba.prototype=new y;function B(a){return a.ma.name}Ba.prototype.m=function(){return(this.ma.isInterface?"interface ":this.ma.isPrimitive?"":"class ")+B(this)};Ba.prototype.a=new x({pc:0},!1,"java.lang.Class",{pc:1,d:1});
function Sa(){this.Dd=null;this.vd=this.rd=this.sd=0}Sa.prototype=new y;function Ta(a){a=a-(1431655765&a>>1)|0;a=(858993459&a)+(858993459&a>>2)|0;return z(16843009,252645135&(a+(a>>4)|0))>>24}function Ua(a,b){var c=b,c=c|c>>>1|0,c=c|c>>>2|0,c=c|c>>>4|0,c=c|c>>>8|0;return 32-Ta(c|c>>>16|0)|0}function Va(a,b){return Ta(-1+(b&(-b|0))|0)}Sa.prototype.a=new x({vc:0},!1,"java.lang.Integer$",{vc:1,d:1});var Wa=void 0;function H(){Wa||(Wa=(new Sa).e());return Wa}function Xa(){}Xa.prototype=new y;
function Ya(){}Ya.prototype=Xa.prototype;function Za(){}Za.prototype=new y;function $a(){}$a.prototype=Za.prototype;function ab(a,b){var c;c=z(-862048943,b);H();c=c<<15|c>>>-15|0;c=z(461845907,c);c^=a;H();return-430675100+z(5,c<<13|c>>>-13|0)|0}function bb(a){a=z(-2048144789,a^(a>>>16|0));a^=a>>>13|0;a=z(-1028477387,a);return a^=a>>>16|0}function cb(a){db();var b=a.Mb();if(0===b)return a=a.Ob(),va(t(),a);for(var c=-889275714,d=0;d<b;)c=ab(c,eb(fb(),a.Nb(d))),d=1+d|0;return bb(c^b)}
function gb(a,b,c){var d=(new D).j(0);c=(new D).j(c);b.z(F(function(a,b,c){return function(a){c.f=ab(c.f,eb(fb(),a));b.f=1+b.f|0}}(a,d,c)));return bb(c.f^d.f)}function Na(a){var b=a.da().W(),c=(new D).j(0);a.z(F(function(a,b,c){return function(a){b.aa(hb(a,c.f));c.f=1+c.f|0}}(a,b,c)));return b.X()}function ib(a,b,c){var d=Qa(!0);I(b.t,c);a.z(F(function(a,b,c,d){return function(a){if(b.f)jb(c,a),b.f=!1;else return I(c.t,d),jb(c,a)}}(a,d,b,", ")));I(b.t,")");return b}function kb(){}kb.prototype=new y;
function lb(){}lb.prototype=kb.prototype;function mb(a,b){var c;c=0;for(var d=a;;)if(c<b&&d.s!==d)d=d.s,c=1+c|0;else break;c=d;if(!c.v())return c.ea;throw(new J).n(""+b);}function nb(a){if(a.v())throw(new K).n("requirement failed: tail of empty list");return a.s}function ob(a){if(a.s===a)throw(new N).e();return a.ea}function pb(a,b){if(b>=a.E)throw(new J).n(""+b);return a.M.G[b]}function qb(){this.L=!1;this.Cb=this.gc=this.oa=this.ca=null;this.gb=!1;this.Jb=this.Fb=0}qb.prototype=new y;
qb.prototype.e=function(){rb=this;this.ca=(this.L=!!(p.ArrayBuffer&&p.Int32Array&&p.Float32Array&&p.Float64Array))?new p.ArrayBuffer(8):null;this.oa=this.L?new p.Int32Array(this.ca,0,2):null;this.gc=this.L?new p.Float32Array(this.ca,0,2):null;this.Cb=this.L?new p.Float64Array(this.ca,0,1):null;if(this.L)this.oa[0]=16909060,a=1===((new p.Int8Array(this.ca,0,8))[0]|0);else var a=!0;this.Fb=(this.gb=a)?0:1;this.Jb=this.gb?1:0;return this};
function wa(a,b){var c=b|0;if(c===b&&-Infinity!==1/b)return c;if(a.L)a.Cb[0]=b,c=sb(tb((new O).j(a.oa[a.Fb]|0),32),ub((new O).h(4194303,1023,0),(new O).j(a.oa[a.Jb]|0)));else{if(b!==b)var c=!1,d=2047,e=+p.Math.pow(2,51);else if(Infinity===b||-Infinity===b)c=0>b,d=2047,e=0;else if(0===b)c=-Infinity===1/b,e=d=0;else{var f=(c=0>b)?-b:b;if(f>=+p.Math.pow(2,-1022)){var d=+p.Math.pow(2,52),e=+p.Math.log(f)/0.6931471805599453,e=+p.Math.floor(e)|0,e=1023>e?e:1023,m=f/+p.Math.pow(2,e)*d,f=+p.Math.floor(m),
m=m-f,f=0.5>m?f:0.5<m?1+f:0!==f%2?1+f:f;2<=f/d&&(e=1+e|0,f=1);1023<e?(e=2047,f=0):(e=1023+e|0,f-=d);d=e;e=f}else d=f/+p.Math.pow(2,-1074),e=+p.Math.floor(d),f=d-e,d=0,e=0.5>f?e:0.5<f?1+e:0!==e%2?1+e:e}e=+ +e;f=e|0;c=sb(tb((new O).j((c?-2147483648:0)|(d|0)<<20|e/4294967296|0),32),ub((new O).h(4194303,1023,0),(new O).j(f)))}return P(vb(c,wb(c,32)))}qb.prototype.a=new x({bd:0},!1,"scala.scalajs.runtime.Bits$",{bd:1,d:1});var rb=void 0;function xa(){rb||(rb=(new qb).e());return rb}function xb(){}
xb.prototype=new y;function va(a,b){for(var c=0,d=1,e=-1+(b.length|0)|0;0<=e;)c=c+z(65535&(b.charCodeAt(e)|0),d)|0,d=z(31,d),e=-1+e|0;return c}function yb(a){if(0===(-65536&a)){var b=p.String,c=b.fromCharCode;a=[a];a=[].concat(a);return c.apply(b,a)}if(0>a||1114111<a)throw(new K).e();a=-65536+a|0;b=p.String;c=b.fromCharCode;a=[55296|a>>10,56320|1023&a];a=[].concat(a);return c.apply(b,a)}xb.prototype.a=new x({dd:0},!1,"scala.scalajs.runtime.RuntimeString$",{dd:1,d:1});var zb=void 0;
function t(){zb||(zb=(new xb).e());return zb}function Ab(){this.Gd=!1;this.dc=this.yb=this.ec=null;this.Fd=!1}Ab.prototype=new y;
Ab.prototype.e=function(){Bb=this;for(var a={O:"java_lang_Object",T:"java_lang_String",V:"scala_Unit",Z:"scala_Boolean",C:"scala_Char",B:"scala_Byte",S:"scala_Short",I:"scala_Int",J:"scala_Long",F:"scala_Float",D:"scala_Double"},b=0;22>=b;)2<=b&&(a["T"+b]="scala_Tuple"+b),a["F"+b]="scala_Function"+b,b=1+b|0;this.ec=a;this.yb={sjsr_:"scala_scalajs_runtime_",sjs_:"scala_scalajs_",sci_:"scala_collection_immutable_",scm_:"scala_collection_mutable_",scg_:"scala_collection_generic_",sc_:"scala_collection_",
sr_:"scala_runtime_",s_:"scala_",jl_:"java_lang_",ju_:"java_util_"};this.dc=p.Object.keys(this.yb);return this};Ab.prototype.a=new x({ed:0},!1,"scala.scalajs.runtime.StackTrace$",{ed:1,d:1});var Bb=void 0;function Cb(){Bb||(Bb=(new Ab).e());return Bb}function Db(){}Db.prototype=new y;function Eb(a,b){return b&&b.a&&b.a.y.sb?b.Q:b}function Fb(a,b){return b&&b.a&&b.a.y.P?b:(new Gb).lb(b)}Db.prototype.a=new x({fd:0},!1,"scala.scalajs.runtime.package$",{fd:1,d:1});var Hb=void 0;
function Q(){Hb||(Hb=(new Db).e());return Hb}var qa=new x({hd:0},!1,"scala.runtime.BoxedUnit",{hd:1,d:1},void 0,function(a){return void 0===a});function Ib(){}Ib.prototype=new y;Ib.prototype.a=new x({id:0},!1,"scala.runtime.BoxesRunTime$",{id:1,d:1});var Jb=void 0;function Kb(){}Kb.prototype=new y;
function eb(a,b){var c;if(null===b)c=0;else if(b&&b.a&&b.a.y.N||"number"===typeof b)if(Jb||(Jb=(new Ib).e()),(b|0)===b&&1/b!==1/-0)c=b|0;else if(sa(b))c=P(ya(b)),c=R((new O).j(c),ya(b))?c:P(vb(ya(b),wb(ya(b),32)));else if("number"===typeof b){var d=+b|0;c=+b;d===c?c=d:(d=Lb(w(),+b),c=Mb(d)===c?P(vb(d,wb(d,32))):wa(xa(),+b))}else c=ua(b);else c=ua(b);return c}Kb.prototype.a=new x({ld:0},!1,"scala.runtime.ScalaRunTime$",{ld:1,d:1});var Nb=void 0;function fb(){Nb||(Nb=(new Kb).e());return Nb}
var pa=new x({mc:0},!1,"java.lang.Boolean",{mc:1,d:1,x:1},void 0,function(a){return"boolean"===typeof a});function Ob(){this.cb=0}Ob.prototype=new y;Ob.prototype.m=function(){return p.String.fromCharCode(this.cb)};function Pb(a){var b=new Ob;b.cb=a;return b}Ob.prototype.r=aa("cb");Ob.prototype.a=new x({oc:0},!1,"java.lang.Character",{oc:1,d:1,x:1});function S(){this.de=this.fc=this.Pb=null}S.prototype=new y;function Qb(){}Qb.prototype=S.prototype;
S.prototype.Bb=function(){var a=Cb(),b;a:try{b=a.undef()}catch(c){a=Fb(Q(),c);if(null!==a){if(a&&a.a&&a.a.y.sb){b=a.Q;break a}throw Eb(Q(),a);}throw c;}this.stackdata=b;return this};S.prototype.Db=aa("Pb");S.prototype.m=function(){var a=B(ha(this)),b=this.Db();return null===b?a:a+": "+b};S.prototype.na=function(a,b){this.Pb=a;this.fc=b;this.Bb();return this};function T(){this.Id=this.qb=this.pb=0;this.Eb=!1}T.prototype=new y;T.prototype.e=function(){T.prototype.ic.call(this,Rb());return this};
T.prototype.ic=function(a){this.Eb=!1;a=ub((new O).h(4194303,4194303,15),vb((new O).h(2942573,6011,0),a));this.pb=P(wb(a,24));this.qb=16777215&P(a);this.Eb=!1;return this};function Ja(a,b){if(0>=b)throw(new K).n("n must be positive");var c;if((b&(-b|0))===b)c=P(Sb(Ub((new O).j(b),(new O).j(Vb(a))),31));else a:{for(;;){c=Vb(a);var d=c%b;if(!(0>((c-d|0)+(-1+b|0)|0))){c=d;break a}}c=void 0}return c}
function Vb(a){var b=a.qb,c=11+15525485*b,b=16777215&((c/16777216|0)+(16777215&(1502*b+15525485*a.pb|0))|0),c=16777215&(c|0);a.pb=b;a.qb=c;return(b<<8|c>>16)>>>1|0}T.prototype.a=new x({Bc:0},!1,"java.util.Random",{Bc:1,d:1,i:1});function Wb(){this.$c=this.Dc=this.rb=this.he=this.fe=this.Jd=this.ee=this.Ed=0}Wb.prototype=new $a;Wb.prototype.e=function(){Xb=this;this.rb=va(t(),"Seq");this.Dc=va(t(),"Map");this.$c=va(t(),"Set");return this};
function Yb(a){var b=db(),c;if(a&&a.a&&a.a.y.Vd){c=0;for(var b=b.rb,d=a;!d.v();)a=d.kb(),d=d.ge(),b=ab(b,eb(fb(),a)),c=1+c|0;c=bb(b^c)}else c=gb(b,a,b.rb);return c}Wb.prototype.a=new x({Hc:0},!1,"scala.util.hashing.MurmurHash3$",{Hc:1,Od:1,d:1});var Xb=void 0;function db(){Xb||(Xb=(new Wb).e());return Xb}function Zb(){this.$=this.Lb=null}Zb.prototype=new y;Zb.prototype.z=function(a){this.$.z(F(function(a,c){return function(d){return a.Lb.u(d)?c.u(d):void 0}}(this,a)))};
Zb.prototype.a=new x({Mc:0},!1,"scala.collection.TraversableLike$WithFilter",{Mc:1,d:1,ga:1});function $b(){this.H=null}$b.prototype=new lb;function ac(){}ac.prototype=$b.prototype;$b.prototype.e=function(){this.H=(new bc).mb(this);return this};function cc(){this.$=null}cc.prototype=new y;function dc(){}dc.prototype=cc.prototype;cc.prototype.mb=function(a){if(null===a)throw Eb(Q(),null);this.$=a;return this};function ec(){}ec.prototype=new y;function fc(){}fc.prototype=ec.prototype;
ec.prototype.m=g("\x3cfunction1\x3e");function gc(){this.f=!1}gc.prototype=new y;gc.prototype.m=function(){return""+this.f};function Qa(a){var b=new gc;b.f=a;return b}gc.prototype.a=new x({gd:0},!1,"scala.runtime.BooleanRef",{gd:1,d:1,i:1});function hc(){this.f=0}hc.prototype=new y;function Da(a){var b=new hc;b.f=a;return b}hc.prototype.m=function(){return""+this.f};hc.prototype.a=new x({jd:0},!1,"scala.runtime.DoubleRef",{jd:1,d:1,i:1});function D(){this.f=0}D.prototype=new y;
D.prototype.m=function(){return""+this.f};D.prototype.j=function(a){this.f=a;return this};D.prototype.a=new x({kd:0},!1,"scala.runtime.IntRef",{kd:1,d:1,i:1});var ja=new x({nc:0},!1,"java.lang.Byte",{nc:1,N:1,d:1,x:1},void 0,function(a){return a<<24>>24===a&&1/a!==1/-0}),oa=new x({qc:0},!1,"java.lang.Double",{qc:1,N:1,d:1,x:1},void 0,function(a){return"number"===typeof a});function ic(){S.call(this)}ic.prototype=new Qb;function jc(){}jc.prototype=ic.prototype;
var na=new x({rc:0},!1,"java.lang.Float",{rc:1,N:1,d:1,x:1},void 0,function(a){return a!==a||ma(a)===a}),la=new x({uc:0},!1,"java.lang.Integer",{uc:1,N:1,d:1,x:1},void 0,function(a){return(a|0)===a&&1/a!==1/-0}),ta=new x({wc:0},!1,"java.lang.Long",{wc:1,N:1,d:1,x:1},void 0,function(a){return sa(a)}),ka=new x({yc:0},!1,"java.lang.Short",{yc:1,N:1,d:1,x:1},void 0,function(a){return a<<16>>16===a&&1/a!==1/-0});function kc(){}kc.prototype=new y;
function Rb(){lc||(lc=(new kc).e());return sb(tb((new O).j(mc()),32),ub((new O).h(4194303,1023,0),(new O).j(mc())))}function mc(){var a=4294967296*+p.Math.random();return-2147483648+ +p.Math.floor(a)|0}kc.prototype.a=new x({Cc:0},!1,"java.util.Random$",{Cc:1,d:1,p:1,i:1});var lc=void 0;function nc(){this.A=null}nc.prototype=new y;function oc(){}oc.prototype=nc.prototype;nc.prototype.e=function(){nc.prototype.kc.call(this,(new T).e());return this};nc.prototype.kc=function(a){this.A=a;return this};
function pc(){this.H=null}pc.prototype=new ac;function qc(){}qc.prototype=pc.prototype;function bc(){this.Yb=this.$=null}bc.prototype=new dc;bc.prototype.mb=function(a){if(null===a)throw Eb(Q(),null);this.Yb=a;cc.prototype.mb.call(this,a);return this};bc.prototype.a=new x({Nc:0},!1,"scala.collection.generic.GenTraversableFactory$$anon$1",{Nc:1,Sd:1,d:1,Rd:1});function rc(){this.Ab=null}rc.prototype=new fc;rc.prototype.u=function(a){return(0,this.Ab)(a)};
function F(a){var b=new rc;b.Ab=a;return b}rc.prototype.a=new x({ad:0},!1,"scala.scalajs.runtime.AnonFunction1",{ad:1,ce:1,d:1,ba:1});function O(){this.b=this.c=this.g=0}O.prototype=new Ya;function sb(a,b){return(new O).h(a.g|b.g,a.c|b.c,a.b|b.b)}
function Ub(a,b){var c=8191&a.g,d=a.g>>13|(15&a.c)<<9,e=8191&a.c>>4,f=a.c>>17|(255&a.b)<<5,m=(1048320&a.b)>>8;c|=0;d|=0;e|=0;f|=0;m|=0;var l=8191&b.g,n=b.g>>13|(15&b.c)<<9,u=8191&b.c>>4,r=b.c>>17|(255&b.b)<<5,v=(1048320&b.b)>>8;var l=l|0,n=n|0,u=u|0,da=r|0,Tb=v|0,L=z(c,l),M=z(d,l),v=z(e,l),r=z(f,l),m=z(m,l);0!==n&&(M=M+z(c,n)|0,v=v+z(d,n)|0,r=r+z(e,n)|0,m=m+z(f,n)|0);0!==u&&(v=v+z(c,u)|0,r=r+z(d,u)|0,m=m+z(e,u)|0);0!==da&&(r=r+z(c,da)|0,m=m+z(d,da)|0);0!==Tb&&(m=m+z(c,Tb)|0);c=(4194303&L)+((511&M)<<
13)|0;d=((((L>>22)+(M>>9)|0)+((262143&v)<<4)|0)+((31&r)<<17)|0)+(c>>22)|0;return(new O).h(4194303&c,4194303&d,1048575&((((v>>18)+(r>>5)|0)+((4095&m)<<8)|0)+(d>>22)|0))}h=O.prototype;h.h=function(a,b,c){this.g=a;this.c=b;this.b=c;return this};
h.m=function(){if(0===this.g&&0===this.c&&0===this.b)return"0";if(R(this,w().K))return"-9223372036854775808";if(0!==(524288&this.b))return"-"+U(this).m();var a=w().ub,b=this,c="";for(;;){var d=b;if(0===d.g&&0===d.c&&0===d.b)return c;d=sc(b,a);b=d[0];d=""+P(d[1]);c=(0===b.g&&0===b.c&&0===b.b?"":"000000000".substring(d.length|0))+d+c}};
function sc(a,b){if(0===b.g&&0===b.c&&0===b.b)throw(new tc).n("/ by zero");if(0===a.g&&0===a.c&&0===a.b)return[w().q,w().q];if(R(b,w().K))return R(a,w().K)?[w().fb,w().q]:[w().q,a];var c=0!==(524288&a.b),d=0!==(524288&b.b),e=R(a,w().K),f=0===b.b&&0===b.c&&0!==b.g&&0===(b.g&(-1+b.g|0))?Va(H(),b.g):0===b.b&&0!==b.c&&0===b.g&&0===(b.c&(-1+b.c|0))?22+Va(H(),b.c)|0:0!==b.b&&0===b.c&&0===b.g&&0===(b.b&(-1+b.b|0))?44+Va(H(),b.b)|0:-1;if(0<=f){if(e)return c=Sb(a,f),[d?U(c):c,w().q];var e=0!==(524288&a.b)?
U(a):a,m=Sb(e,f),d=c!==d?U(m):m,e=22>=f?(new O).h(e.g&(-1+(1<<f)|0),0,0):44>=f?(new O).h(e.g,e.c&(-1+(1<<(-22+f|0))|0),0):(new O).h(e.g,e.c,e.b&(-1+(1<<(-44+f|0))|0)),c=c?U(e):e;return[d,c]}f=0!==(524288&b.b)?U(b):b;if(e)var l=w().eb;else{var n=0!==(524288&a.b)?U(a):a;if(uc(f,n))return[w().q,a];l=n}var n=vc(f)-vc(l)|0,u=tb(f,n),f=n,n=u,u=l,l=w().q;a:for(;;){if(0>f)var r=!0;else r=u,r=0===r.g&&0===r.c&&0===r.b;if(r){var v=l,m=u;break a}else r=wc(u,U(n)),0===(524288&r.b)?(u=-1+f|0,n=Sb(n,1),l=22>f?
(new O).h(l.g|1<<f,l.c,l.b):44>f?(new O).h(l.g,l.c|1<<(-22+f|0),l.b):(new O).h(l.g,l.c,l.b|1<<(-44+f|0)),f=u,u=r):(f=-1+f|0,n=Sb(n,1))}d=c!==d?U(v):v;c&&e?(c=U(m),e=w().fb,c=wc(c,U(e))):c=c?U(m):m;return[d,c]}function ub(a,b){return(new O).h(a.g&b.g,a.c&b.c,a.b&b.b)}
function wb(a,b){var c=63&b;if(22>c){var d=22-c|0;return(new O).h(4194303&(a.g>>c|a.c<<d),4194303&(a.c>>c|a.b<<d),1048575&(a.b>>>c|0))}return 44>c?(d=-22+c|0,(new O).h(4194303&(a.c>>d|a.b<<(44-c|0)),4194303&(a.b>>>d|0),0)):(new O).h(4194303&(a.b>>>(-44+c|0)|0),0,0)}function uc(a,b){return 0===(524288&a.b)?0!==(524288&b.b)||a.b>b.b||a.b===b.b&&a.c>b.c||a.b===b.b&&a.c===b.c&&a.g>b.g:!(0===(524288&b.b)||a.b<b.b||a.b===b.b&&a.c<b.c||a.b===b.b&&a.c===b.c&&a.g<=b.g)}
function tb(a,b){var c=63&b;if(22>c){var d=22-c|0;return(new O).h(4194303&a.g<<c,4194303&(a.c<<c|a.g>>d),1048575&(a.b<<c|a.c>>d))}return 44>c?(d=-22+c|0,(new O).h(0,4194303&a.g<<d,1048575&(a.c<<d|a.g>>(44-c|0)))):(new O).h(0,0,1048575&a.g<<(-44+c|0))}function P(a){return a.g|a.c<<22}h.j=function(a){O.prototype.h.call(this,4194303&a,4194303&a>>22,0>a?1048575:0);return this};
function U(a){var b=4194303&(1+~a.g|0),c=4194303&(~a.c+(0===b?1:0)|0);return(new O).h(b,c,1048575&(~a.b+(0===b&&0===c?1:0)|0))}function wc(a,b){var c=a.g+b.g|0,d=(a.c+b.c|0)+(c>>22)|0;return(new O).h(4194303&c,4194303&d,1048575&((a.b+b.b|0)+(d>>22)|0))}
function Sb(a,b){var c=63&b,d=0!==(524288&a.b),e=d?-1048576|a.b:a.b;if(22>c)return d=22-c|0,(new O).h(4194303&(a.g>>c|a.c<<d),4194303&(a.c>>c|e<<d),1048575&e>>c);if(44>c){var f=-22+c|0;return(new O).h(4194303&(a.c>>f|e<<(44-c|0)),4194303&e>>f,1048575&(d?1048575:0))}return(new O).h(4194303&e>>(-44+c|0),4194303&(d?4194303:0),1048575&(d?1048575:0))}function Mb(a){return R(a,w().K)?-9223372036854775E3:0!==(524288&a.b)?-Mb(U(a)):a.g+4194304*a.c+17592186044416*a.b}
function vc(a){return 0!==a.b?-12+Ua(H(),a.b)|0:0!==a.c?10+Ua(H(),a.c)|0:32+Ua(H(),a.g)|0}h.r=function(){return P(vb(this,wb(this,32)))};function vb(a,b){return(new O).h(a.g^b.g,a.c^b.c,a.b^b.b)}function R(a,b){return a.g===b.g&&a.c===b.c&&a.b===b.b}function sa(a){return!!(a&&a.a&&a.a.y.Ub)}h.a=new x({Ub:0},!1,"scala.scalajs.runtime.RuntimeLong",{Ub:1,N:1,d:1,x:1});
function xc(){this.Cd=this.Bd=this.Ad=this.zd=this.yd=this.xd=this.wd=this.ud=this.td=this.qd=this.pd=this.od=this.nd=this.md=0;this.ub=this.eb=this.K=this.ac=this.fb=this.q=null}xc.prototype=new y;xc.prototype.e=function(){yc=this;this.q=(new O).h(0,0,0);this.fb=(new O).h(1,0,0);this.ac=(new O).h(4194303,4194303,1048575);this.K=(new O).h(0,0,524288);this.eb=(new O).h(4194303,4194303,524287);this.ub=(new O).h(1755648,238,0);return this};
function Lb(a,b){if(b!==b)return a.q;if(-9223372036854775E3>b)return a.K;if(9223372036854775E3<=b)return a.eb;if(0>b)return U(Lb(a,-b));var c=b,d=17592186044416<=c?c/17592186044416|0:0,c=c-17592186044416*d,e=4194304<=c?c/4194304|0:0;return(new O).h(c-4194304*e|0,e,d)}xc.prototype.a=new x({cd:0},!1,"scala.scalajs.runtime.RuntimeLong$",{cd:1,d:1,p:1,i:1});var yc=void 0;function w(){yc||(yc=(new xc).e());return yc}
var ia=new x({bc:0},!1,"java.lang.String",{bc:1,d:1,i:1,Ib:1,x:1},void 0,function(a){return"string"===typeof a});function V(){S.call(this)}V.prototype=new jc;function W(){}W.prototype=V.prototype;V.prototype.e=function(){V.prototype.na.call(this,null,null);return this};V.prototype.n=function(a){V.prototype.na.call(this,a,null);return this};function X(){this.w=null}X.prototype=new y;function I(a,b){a.w=""+a.w+(null===b?"null":b);return a}X.prototype.m=aa("w");
X.prototype.j=function(){X.prototype.n.call(this,"");return this};X.prototype.n=function(a){this.w=a;return this};X.prototype.a=new x({zc:0},!1,"java.lang.StringBuilder",{zc:1,d:1,Ib:1,Hd:1,i:1});function Ia(){this.A=null}Ia.prototype=new oc;Ia.prototype.a=new x({Gc:0},!1,"scala.util.Random$",{Gc:1,Nd:1,d:1,p:1,i:1});var Ha=void 0;function tc(){S.call(this)}tc.prototype=new W;tc.prototype.a=new x({lc:0},!1,"java.lang.ArithmeticException",{lc:1,U:1,R:1,P:1,d:1,i:1});function K(){S.call(this)}
K.prototype=new W;K.prototype.e=function(){K.prototype.na.call(this,null,null);return this};K.prototype.n=function(a){K.prototype.na.call(this,a,null);return this};K.prototype.a=new x({sc:0},!1,"java.lang.IllegalArgumentException",{sc:1,U:1,R:1,P:1,d:1,i:1});function J(){S.call(this)}J.prototype=new W;J.prototype.a=new x({tc:0},!1,"java.lang.IndexOutOfBoundsException",{tc:1,U:1,R:1,P:1,d:1,i:1});function ra(){S.call(this)}ra.prototype=new W;
ra.prototype.e=function(){ra.prototype.n.call(this,null);return this};ra.prototype.a=new x({xc:0},!1,"java.lang.NullPointerException",{xc:1,U:1,R:1,P:1,d:1,i:1});function N(){S.call(this)}N.prototype=new W;N.prototype.e=function(){N.prototype.n.call(this,null);return this};N.prototype.a=new x({Ac:0},!1,"java.util.NoSuchElementException",{Ac:1,U:1,R:1,P:1,d:1,i:1});function Oa(){S.call(this);this.Kb=this.fa=null;this.jb=!1}Oa.prototype=new W;
Oa.prototype.Db=function(){if(!this.jb&&!this.jb){var a;if(null===this.fa)a="null";else try{a=ga(this.fa)+" ("+("of class "+B(ha(this.fa)))+")"}catch(b){if(null!==Fb(Q(),b))a="an instance of class "+B(ha(this.fa));else throw b;}this.Kb=a;this.jb=!0}return this.Kb};Oa.prototype.lb=function(a){this.fa=a;V.prototype.e.call(this);return this};Oa.prototype.a=new x({Ec:0},!1,"scala.MatchError",{Ec:1,U:1,R:1,P:1,d:1,i:1});function zc(){this.zb=this.A=null}zc.prototype=new y;
function Ac(a,b,c){a.zb=c;a.A=b;return a}h=zc.prototype;h.m=function(){return""+this.A};h.X=function(){return this.zb.u(this.A.X())};h.aa=function(a){this.A.aa(a);return this};h.r=function(){return this.A.r()};h.a=new x({Qc:0},!1,"scala.collection.mutable.Builder$$anon$1",{Qc:1,d:1,Xa:1,Sa:1,Ma:1,Ld:1});function Bc(){this.ia=this.ha=null}Bc.prototype=new y;h=Bc.prototype;h.Ob=g("Tuple2");h.Mb=g(2);
h.Nb=function(a){a:switch(a){case 0:a=this.ha;break a;case 1:a=this.ia;break a;default:throw(new J).n(""+a);}return a};function hb(a,b){var c=new Bc;c.ha=a;c.ia=b;return c}h.m=function(){return"("+this.ha+","+this.ia+")"};h.r=function(){return cb(this)};h.a=new x({cc:0},!1,"scala.Tuple2",{cc:1,d:1,Kd:1,Fc:1,Y:1,p:1,i:1});function Cc(){this.H=null}Cc.prototype=new qc;function Y(){}Y.prototype=Cc.prototype;function Dc(){this.H=null}Dc.prototype=new Y;Dc.prototype.W=function(){return(new Ec).e()};
Dc.prototype.a=new x({Sc:0},!1,"scala.collection.mutable.IndexedSeq$",{Sc:1,Ua:1,Na:1,Oa:1,Pa:1,d:1,Va:1,Qa:1});var Fc=void 0;function Gb(){S.call(this);this.Q=null}Gb.prototype=new W;h=Gb.prototype;h.Ob=g("JavaScriptException");h.Mb=g(1);h.Bb=function(){Cb();this.stackdata=this.Q;return this};h.Nb=function(a){switch(a){case 0:return this.Q;default:throw(new J).n(""+a);}};h.m=function(){return ga(this.Q)};h.lb=function(a){this.Q=a;V.prototype.e.call(this);return this};h.r=function(){return cb(this)};
h.a=new x({sb:0},!1,"scala.scalajs.js.JavaScriptException",{sb:1,U:1,R:1,P:1,d:1,i:1,Fc:1,Y:1,p:1});function Gc(){this.H=null}Gc.prototype=new Y;Gc.prototype.W=function(){return(new Ec).e()};Gc.prototype.a=new x({Pc:0},!1,"scala.collection.mutable.ArrayBuffer$",{Pc:1,Ua:1,Na:1,Oa:1,Pa:1,d:1,Va:1,Qa:1,p:1,i:1});var Hc=void 0;function Ic(){this.H=null}Ic.prototype=new Y;Ic.prototype.W=function(){var a=(new Z).e();return Ac(new zc,a,F(function(){return function(a){return a.k}}(this)))};
Ic.prototype.a=new x({Vc:0},!1,"scala.collection.mutable.LinkedList$",{Vc:1,Ua:1,Na:1,Oa:1,Pa:1,d:1,Va:1,Qa:1,p:1,i:1});var Jc=void 0;function Kc(){this.H=null}Kc.prototype=new Y;Kc.prototype.W=function(){return(new Z).e()};Kc.prototype.a=new x({Wc:0},!1,"scala.collection.mutable.MutableList$",{Wc:1,Ua:1,Na:1,Oa:1,Pa:1,d:1,Va:1,Qa:1,p:1,i:1});var Lc=void 0;function Mc(){this.H=null}Mc.prototype=new Y;
Mc.prototype.W=function(){var a=(new Z).e();return Ac(new zc,a,F(function(){return function(a){var c=a.k,d=a.o;a=a.l;var e=new $;$.prototype.e.call(e);e.k=c;e.o=d;e.l=a;return e}}(this)))};Mc.prototype.a=new x({Yc:0},!1,"scala.collection.mutable.Queue$",{Yc:1,Ua:1,Na:1,Oa:1,Pa:1,d:1,Va:1,Qa:1,p:1,i:1});var Nc=void 0;function Ea(){Nc||(Nc=(new Mc).e());return Nc}function Oc(){}Oc.prototype=new y;function Pc(){}Pc.prototype=Oc.prototype;
function Ma(a,b){var c=new Zb;c.Lb=b;if(null===a)throw Eb(Q(),null);c.$=a;return c}Oc.prototype.Vb=function(){var a=B(ha(this)),b;t();b=a;var c=yb(46);b=b.lastIndexOf(c)|0;-1!==b&&(a=a.substring(1+b|0));t();b=a;c=yb(36);b=b.indexOf(c)|0;-1!==b&&(a=a.substring(0,b));return a};function Qc(){}Qc.prototype=new Pc;function Rc(){}Rc.prototype=Qc.prototype;function Sc(){}Sc.prototype=new Rc;function Tc(){}Tc.prototype=Sc.prototype;
Sc.prototype.m=function(){var a=this.Vb()+"(",b=(new Uc).e();return ib(this,b,a).t.w};function Vc(){}Vc.prototype=new Tc;function Wc(){}Wc.prototype=Vc.prototype;function E(){this.s=this.ea=null}E.prototype=new Wc;h=E.prototype;h.kb=function(){return ob(this)};h.e=function(){this.s=this;return this};h.u=function(a){return mb(this,a|0)};h.v=function(){return this.s===this};h.da=function(){Jc||(Jc=(new Ic).e());return Jc};h.z=function(a){for(var b=this;!b.v();)a.u(b.ea),b=b.s};h.tb=function(){return nb(this)};
h.r=function(){return Yb(this)};h.a=new x({Uc:0},!1,"scala.collection.mutable.LinkedList",{Uc:1,Wa:1,va:1,ua:1,wa:1,d:1,Ja:1,Ka:1,Ta:1,ga:1,La:1,Da:1,Ca:1,Ga:1,Ba:1,Ra:1,Ea:1,xa:1,ya:1,Fa:1,Y:1,Ha:1,ta:1,ba:1,za:1,Aa:1,Ia:1,$a:1,Za:1,bb:1,sa:1,ab:1,Ya:1,ra:1,pa:1,Sb:1,Qb:1,Rb:1,ae:1,p:1,i:1});function Xc(){}Xc.prototype=new Wc;function Yc(){}Yc.prototype=Xc.prototype;function Z(){this.o=this.k=null;this.l=0}Z.prototype=new Wc;function Zc(){}h=Zc.prototype=Z.prototype;
h.kb=function(){if(!this.v())return ob(this.k);throw(new N).e();};h.e=function(){this.o=this.k=(new E).e();this.l=0;return this};h.u=function(a){return mb(this.k,a|0)};h.v=function(){return 0===this.l};h.da=function(){Lc||(Lc=(new Kc).e());return Lc};h.z=function(a){for(var b=this;!b.v();)a.u(b.kb()),b=b.tb()};function $c(a,b){if(a.v())throw(new K).n("requirement failed: tail of empty list");b.k=nb(a.k);b.l=-1+a.l|0;b.o=0===b.l?b.k:a.o}h.X=function(){return this};h.tb=function(){return this.Wb()};
h.aa=function(a){return Ka(this,a)};h.r=function(){return Yb(this)};function Ka(a,b){if(0===a.l){var c=a.k,d=new E;E.prototype.e.call(d);null!==c&&(d.ea=b,d.s=c);a.k=d;0===a.l&&(a.o=a.k)}else a.o.s=(new E).e(),a.o=a.o.s,a.o.ea=b,a.o.s=(new E).e();a.l=1+a.l|0;return a}h.Wb=function(){var a=(new Z).e();$c(this,a);return a};
h.a=new x({Tb:0},!1,"scala.collection.mutable.MutableList",{Tb:1,Wa:1,va:1,ua:1,wa:1,d:1,Ja:1,Ka:1,Ta:1,ga:1,La:1,Da:1,Ca:1,Ga:1,Ba:1,Ra:1,Ea:1,xa:1,ya:1,Fa:1,Y:1,Ha:1,ta:1,ba:1,za:1,Aa:1,Ia:1,$a:1,Za:1,bb:1,sa:1,ab:1,Ya:1,ra:1,pa:1,Sb:1,Qb:1,Rb:1,Lc:1,Xa:1,Sa:1,Ma:1,p:1,i:1});function $(){Z.call(this)}$.prototype=new Zc;function La(a){if(a.v())throw(new N).n("queue empty");a.k=a.k.s;a.l=-1+a.l|0;0===a.l&&(a.o=a.k)}$.prototype.da=function(){return Ea()};$.prototype.tb=function(){return ad(this)};
function ad(a){var b=(new $).e();$c(a,b);return b}$.prototype.Wb=function(){return ad(this)};$.prototype.a=new x({Xc:0},!1,"scala.collection.mutable.Queue",{Xc:1,Tb:1,Wa:1,va:1,ua:1,wa:1,d:1,Ja:1,Ka:1,Ta:1,ga:1,La:1,Da:1,Ca:1,Ga:1,Ba:1,Ra:1,Ea:1,xa:1,ya:1,Fa:1,Y:1,Ha:1,ta:1,ba:1,za:1,Aa:1,Ia:1,$a:1,Za:1,bb:1,sa:1,ab:1,Ya:1,ra:1,pa:1,Sb:1,Qb:1,Rb:1,Lc:1,Xa:1,Sa:1,Ma:1,p:1,i:1});function Uc(){this.t=null}Uc.prototype=new Wc;h=Uc.prototype;h.e=function(){Uc.prototype.hc.call(this,16,"");return this};
h.vb=function(a){a=65535&(this.t.w.charCodeAt(a)|0);return Pb(a)};h.u=function(a){a=65535&(this.t.w.charCodeAt(a|0)|0);return Pb(a)};h.v=function(){return 0===this.qa()};h.m=function(){return this.t.w};h.da=function(){Fc||(Fc=(new Dc).e());return Fc};h.z=function(a){for(var b=0,c=this.qa();b<c;)a.u(this.vb(b)),b=1+b|0};h.X=function(){return this.t.w};h.hc=function(a,b){Uc.prototype.jc.call(this,I((new X).j((b.length|0)+a|0),b));return this};h.qa=function(){return this.t.w.length|0};
h.jc=function(a){this.t=a;return this};function jb(a,b){var c=a.t;t();I(c,null===b?"null":ga(b));return a}h.aa=function(a){I(this.t,p.String.fromCharCode(null===a?0:a.cb));return this};h.r=function(){return Yb(this)};
h.a=new x({Zc:0},!1,"scala.collection.mutable.StringBuilder",{Zc:1,Wa:1,va:1,ua:1,wa:1,d:1,Ja:1,Ka:1,Ta:1,ga:1,La:1,Da:1,Ca:1,Ga:1,Ba:1,Ra:1,Ea:1,xa:1,ya:1,Fa:1,Y:1,Ha:1,ta:1,ba:1,za:1,Aa:1,Ia:1,$a:1,Za:1,bb:1,sa:1,ab:1,Ya:1,ra:1,pa:1,Ib:1,Rc:1,Ic:1,Jc:1,Tc:1,Wd:1,Kc:1,Md:1,x:1,Xa:1,Sa:1,Ma:1,p:1,i:1});function Ec(){this.Gb=0;this.M=null;this.E=0}Ec.prototype=new Yc;h=Ec.prototype;h.e=function(){Ec.prototype.j.call(this,16);return this};h.vb=function(a){return pb(this,a)};
h.u=function(a){return pb(this,a|0)};h.v=function(){return 0===this.qa()};h.da=function(){Hc||(Hc=(new Gc).e());return Hc};h.z=function(a){for(var b=0,c=this.E;b<c;)a.u(this.M.G[b]),b=1+b|0};h.X=function(){return this};h.j=function(a){a=this.Gb=a;var b=Ca(Aa);this.M=fa(b,[1<a?a:1],0);this.E=0;return this};h.qa=aa("E");
h.aa=function(a){var b=1+this.E|0,c=(new O).j(this.M.G.length);if(uc((new O).j(b),c)){for(c=Ub((new O).h(2,0,0),c);uc((new O).j(b),c);)c=Ub((new O).h(2,0,0),c);uc(c,(new O).h(4194303,511,0))&&(c=(new O).h(4194303,511,0));var b=Ca(Aa),b=fa(b,[P(c)],0),c=this.E,d=this.M.G,e=b.G;if(d!==e||0>0+c)for(var f=0;f<c;f++)e[0+f]=d[0+f];else for(f=c-1;0<=f;f--)e[0+f]=d[0+f];this.M=b}this.M.G[this.E]=a;this.E=1+this.E|0;return this};h.r=function(){return Yb(this)};h.Vb=g("ArrayBuffer");
h.a=new x({Oc:0},!1,"scala.collection.mutable.ArrayBuffer",{Oc:1,Xd:1,Wa:1,va:1,ua:1,wa:1,d:1,Ja:1,Ka:1,Ta:1,ga:1,La:1,Da:1,Ca:1,Ga:1,Ba:1,Ra:1,Ea:1,xa:1,ya:1,Fa:1,Y:1,Ha:1,ta:1,ba:1,za:1,Aa:1,Ia:1,$a:1,Za:1,bb:1,sa:1,ab:1,Ya:1,ra:1,pa:1,Yd:1,Zd:1,Sa:1,Ma:1,Td:1,Qd:1,Ud:1,$d:1,Tc:1,Jc:1,Kc:1,Xa:1,be:1,Rc:1,Ic:1,Pd:1,p:1,i:1});}).call(this);
//# sourceMappingURL=example-opt.js.map
