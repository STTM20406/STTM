
function calc_distance(lat1, lon1, lat2, lon2) {

    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2-lat1);  // deg2rad below
    var dLon = deg2rad(lon2-lon1);
    var a =
            Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
            Math.sin(dLon/2) * Math.sin(dLon/2);

    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c; // Distance in km

    return d;
}

function deg2rad(deg) {
  return deg * (Math.PI/180);

}

function offsetTop(_id)
{
    x = document.getElementById(_id);
    var _top = x.offsetTop;

    while(x.offsetParent)
    {
        x = x.offsetParent;
        _top += x.offsetTop;
    }

    return _top;
}

function objOffsetTop(object)
{
    var _top = object.offsetTop;

    while(object.offsetParent)
    {
        object = object.offsetParent;
        _top += object.offsetTop;
    }

    return _top;
}

function offsetLeft(_id)
{
    x = document.getElementById(_id);
    var _left = x.offsetLeft;

    while(x.offsetParent)
    {
        x = x.offsetParent;
        _left += x.offsetLeft;
    }

    return _left;
}

function createMsXmlHttp() {
	try {
		return new ActiveXObject("Microsoft.XMLHTTP");
	} catch(e) {

	}

	try {
		return new ActiveXObject("Msxml2.XMLHTTP");
	} catch(e) {

	}

}

function httpcall(_hc_url, _hcactionfunction)
{
    var xh_rel;
	if (window.ActiveXObject)
	{
		xh_rel = createMsXmlHttp();
	}
	else if (window.XMLHttpRequest)
	{
		xh_rel = new XMLHttpRequest();
	}

	xh_rel.onreadystatechange = function ()
    {
    	if(xh_rel && xh_rel.readyState == 4)
    	{
    		if(xh_rel.status == 200)
    		{
    			_hcactionfunction(xh_rel.responseText);
    		}
    	}
    }

	xh_rel.open("GET", _hc_url, true);
	xh_rel.send(null);

}

function httpcall_post(_hc_url, _query, _hcactionfunction)
{
    var xh_rel;
	if (window.ActiveXObject)
	{
		xh_rel = createMsXmlHttp();
	}
	else if (window.XMLHttpRequest)
	{
		xh_rel = new XMLHttpRequest();
	}

	xh_rel.onreadystatechange = function ()
    {
    	if(xh_rel && xh_rel.readyState == 4)
    	{
    		if(xh_rel.status == 200)
    		{
    			_hcactionfunction(xh_rel.responseText);
    		}
    	}
    }


	xh_rel.open("POST", _hc_url, true);
	xh_rel.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xh_rel.setRequestHeader("Content-length", _query.length);
    xh_rel.setRequestHeader("Connection", "close");
    xh_rel.send(_query);

}

var Base64 = {

	// private property
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

	// public method for encoding
	encode : function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;

		input = Base64._utf8_encode(input);

		while (i < input.length) {

			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);

			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;

			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}

			output = output +
			this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
			this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

		}

		return output;
	},
	// public method for decoding
	decode : function (input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;

		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

		while (i < input.length) {

			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));

			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;

			output = output + String.fromCharCode(chr1);

			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}

		}

		output = Base64._utf8_decode(output);

		return output;

	},

	// private method for UTF-8 encoding
	_utf8_encode : function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);
            if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	},

	// private method for UTF-8 decoding
	_utf8_decode : function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;

		while ( i < utftext.length ) {

			c = utftext.charCodeAt(i);

			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}

		}

		return string;
	},

	URLEncode : function (string) {
	    _t = escape(this._utf8_encode(string));
	    _t = _t.split("+").join("%2B");
        return _t;
    },

    // public method for url decoding
    URLDecode : function (string) {
        return this._utf8_decode(unescape(string));
    }
}

function check_jongsong(txt)
{
	var code = txt.charCodeAt(txt.length-1) - 44032;
	var cho = 19, jung = 21, jong=28;
	var i1, i2, code1, code2;

	if (txt.length == 0) return false;

	if (code < 0 || code > 11171) return false;

	if (code % 28 == 0) return false;

    return true;
}
