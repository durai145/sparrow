var tlv= function heaerieUssServiceProvider() {
    var GenHtmlTemplateFromSJson = false;

    this.getDataType = function (tag) {
      var tagLib = {
        "C1": "ascii"
        , "C2": "ascii"
        , "C3": "ascii"
        , "C4": "ascii"
        , "C5": "ascii"
        , "C6": "ascii"
        , "C7": "ascii"
        , "C8": "ascii"
        , "C9": "ascii"
        , "CA": "ascii"
        , "CB": "ascii"
        , "CC": "ascii"
        , "CD": "ascii"
        , "CF": "ascii"
        , "DF01": "ascii"
        , "DF02": "ascii"
        , "DF03": "ascii"
        , "DF04": "ascii"
        , "DF05": "ascii"
        , "DF06": "ascii"
        , "DF07": "ascii"
        , "DF08": "ascii"
        , "DF09": "ascii"
        , "DF0A": "ascii"
        , "DF0B": "ascii"
        , "DF0C": "ascii"
        , "DF0D": "ascii"
        , "DF0E": "ascii"
        , "DF0F": "ascii"
        , "DF11": "ascii"
        , "DF12": "ascii"
        , "DF13": "ascii"
        , "E1": "ascii"
      };

      return tagLib[tag.toUpperCase()];
    }

    this.getTagDescr = function (tag) {
      var tagLib = {
        "C1": "group"
        , "C2": "name"
        , "C3": "label"
        , "C4": "task"
        , "C5": "desc"
        , "C6": "htmlType"
        , "C7": "entitle"
        , "C8": "enttlname"
        , "C9": "mndf"
        , "CA": "dataType"
        , "CB": "cclass"
        , "CC": "parent"
        , "CD": "parentHtmlType"
        , "CF": "validate"
        , "DF01": "dflt"
        , "DF02": "min"
        , "DF03": "max"
        , "DF04": "tips"
        , "DF05": "onkeyup"
        , "DF06": "onchange"
        , "DF07": "onkeydown"
        , "DF08": "onkeypress"
        , "DF09": "onclick"
        , "DF0A": "onblure"
        , "DF0B": "listVal"
        , "DF0C": "help"
        , "DF0D": "helpLink"
        , "DF0E": "xml"
        , "DF0F": "xmlname"
        , "DF11": "Xpath"
        , "DF12": "maxCol"
        , "DF13": "col"
        , "E1": "childs"
      };

      return tagLib[tag.toUpperCase()];
    }



    this.getTag = function (tag) {
      var tagLib = {
        "group": "C1"
        , "name": "C2"
        , "label": "C3"
        , "task": "C4"
        , "desc": "C5"
        , "htmlType": "C6"
        , "entitle": "C7"
        , "enttlname": "C8"
        , "mndf": "C9"
        , "dataType": "CA"
        , "cclass": "CB"
        , "parent": "CC"
        , "parentHtmlType": "CD"
        , "validate": "CF"
        , "dflt": "DF01"
        , "min": "DF02"
        , "max": "DF03"
        , "tips": "DF04"
        , "onkeyup": "DF05"
        , "onchange": "DF06"
        , "onkeydown": "DF07"
        , "onkeypress": "DF08"
        , "onclick": "DF09"
        , "onblure": "DF0A"
        , "listVal": "DF0B"
        , "help": "DF0C"
        , "helpLink": "DF0D"
        , "xml": "DF0E"
        , "xmlname": "DF0F"
        , "Xpath": "DF11"
        , "maxCol": "DF12"
        , "col": "DF13"
        , "childs": "E1"
      }

      return tagLib[tag];
    }

    this.FunTest = function () {
      alert("Test");
    }

    this.intToHexChar = function (inp) {
      var lib =
      {
        0: '0'
        , 1: '1'
        , 2: '2'
        , 3: '3'
        , 4: '4'
        , 5: '5'
        , 6: '6'
        , 7: '7'
        , 8: '8'
        , 8: '8'
        , 9: '9'
        , 10: 'A'
        , 11: 'B'
        , 12: 'C'
        , 13: 'D'
        , 14: 'E'
        , 15: 'F'
      }

      return lib[inp];
    }

    this.bytesToAscii = function (inpBytes) {
      var rtString = "";
      for (var i = 0; i < inpBytes.length; i++) {
        rtString += String.fromCharCode(inpBytes[i]);
      }

      return rtString;
    }

    this.ByteSubstr1 = function (byteArr, start) {
      var rtbyte = [];
      for (var i = start; i < byteArr.length; i++) {
        rtbyte.push(byteArr[i]);
      }

      return rtbyte;
    }

    this.ByteSubstr2 = function (byteArr, start, endlength) {
      var rtbyte = [];
      for (var i = start; i < byteArr.length && i < endlength; i++) {
        rtbyte.push(byteArr[i]);

      }

      return rtbyte;
    }

    this.parseTVL = function (inpBytes) {

      var parentJson = [];
      var s = 0x80;
      var classVal = 0;
      var primitiveOrConst = 0;
      var tagLen = 0;
      var tagByte = [];
      var highByte = 0;
      var lowByte = 0;
      var mode = ["tag", "length", "value"];
      var modeCnt = 0;
      var leadingOctet = 1; //subsequent
      //.charCodeAt()
      var firstBit = 0;
      var nextSubsequent = 0
      var nextSubSeqByte = 0;
      var lengthSize = 0;
      var len = [];
      var lenByte = [];
      var dataByte = [];
      var data = [];
      var ascii = [];
      var lengthHeaderCnt = 0;

      for (var i = 0; i < inpBytes.length; i++) {
        firstBit = 0;
        highByte = 0;
        lowByte = 0;
        s = 0x80;
        nextSubSeqByte = 0;

        //pre processing

        for (var b = 8; b > 0; b--) {
          if (inpBytes[i] & s) {
            if (b == 8) {


              firstBit = firstBit | s;
            }


            if (b == 8 || b == 7 || b == 6 || b == 5) {
              // if(inpBytes[i]&s)
              highByte = highByte | s;
            }
            if (b == 4 || b == 3 || b == 2 || b == 1) {
              //if(inpBytes[i]&s)
              lowByte = lowByte | s;
            }
            if (b == 5 || b == 4 || b == 3 || b == 2 || b == 1) {
              //if(inpBytes[i]&s)
              nextSubSeqByte = nextSubSeqByte | s;
            }

            if (modeCnt == 0) {
              if (leadingOctet == 1) {
                if (b == 8 || b == 7) {
                  classVal = classVal | s;
                }
                if (b == 6) {
                  //if(inpBytes[i]&s)
                  primitiveOrConst = primitiveOrConst | s;
                }
              }
            }
            else if (modeCnt == 1) {
              if (leadingOctet == 1) {
                if (firstBit == 1) {
                  if (b == 4 || b == 3 || b == 2 || b == 1) {
                    lengthSize = lengthSize | s;
                  }
                }
              }
            }
            else if (modeCnt >= 2) {
              if (b == 8 || b == 7 || b == 6 || b == 5) {
                // if(inpBytes[i]&s)
                highByte = highByte | s;
              }
              if (b == 4 || b == 3 || b == 2 || b == 1) {
                //if(inpBytes[i]&s)
                lowByte = lowByte | s;
              }
            }
          }

          s = s >> 1;

        }
        highByte = highByte >> 4;
        firstBit = firstBit >> 7;
        //decision processing  part

        if (modeCnt == 0) {
          if ((leadingOctet == 1 && nextSubSeqByte == 0x1F) || (leadingOctet == 0 && firstBit == 1)) {
            nextSubsequent = 1;
          }
          else {
            nextSubsequent = 0;
          }

        }
        else
          if (modeCnt == 1) //length 
          {

            if (firstBit == 1) {
              lengthSize += 1;
              nextSubsequent = 1;
            }
            else {
              nextSubsequent = 0;
            }

          }

        //Action Part
        if (leadingOctet == 1 || nextSubsequent == 1) {
          if (modeCnt == 0) {
            tagByte.push(this.intToHexChar(highByte));
            tagByte.push(this.intToHexChar(lowByte));
            // nextSubsequent=1;
            tagLen++;
          }
          else if (modeCnt == 1) {
            len.push(this.intToHexChar(highByte));
            len.push(this.intToHexChar(lowByte));
            lenByte.push(highByte << 4 | lowByte);
            lengthSize--;
            tagLen++;
          }
          else if (modeCnt >= 2) {
            data.push(this.intToHexChar(highByte));
            data.push(this.intToHexChar(lowByte));

            // ascii.push(String.fromCharCode(inpBytes[i])) ;
            dataByte.push(inpBytes[i]);
            tagLen++;
          }
        }

        if (modeCnt == 0 && nextSubsequent == 0 && leadingOctet == 0) {
          tagByte.push(this.intToHexChar(highByte));
          tagByte.push(this.intToHexChar(lowByte));
          // nextSubsequent=1;
          tagLen++;
        }

        if ((modeCnt == 1 && nextSubsequent == 0) && (leadingOctet != 1)) {
          len.push(this.intToHexChar(highByte));
          len.push(this.intToHexChar(lowByte));
          lenByte.push(highByte << 4 | lowByte);
          lengthSize--;
          tagLen++;
        }
        // post procesing   
        if (leadingOctet == 1) {
          leadingOctet = 0;
        }
        if (nextSubsequent == 0) {
          leadingOctet = 1;
          modeCnt++;
          tagLen = 0;
        }

      }


      classVal = classVal >> 6;
      primitiveOrConst = primitiveOrConst >> 5;

      var parentObj = [];
      var lenVal = this.getLengthVal(lenByte);
      var dataHex = this.hexArrToString(data);
      var remData = dataHex.substr(lenVal * 2);
      var value = dataHex.substr(0, lenVal * 2);
      var valueByte = this.ByteSubstr2(dataByte, 0, lenVal);
      var remDataByte = this.ByteSubstr1(dataByte, lenVal);
      var tag = this.hexArrToString(tagByte);
      ascii = this.bytesToAscii(valueByte);
      parentObj = {
        'class': classVal
        , 'primitiveOrConst': primitiveOrConst
        //,'tagByte'          : tagByte
        , 'tag': tag
        , 'lenVal': lenVal
        , 'tagDescr': this.getTagDescr(tag)
        , 'lenVal': lenVal
        , 'value': value
        , 'ASCII': ascii //this.hexArrToString(this.hexToBytes(dataHex))
        , 'childs': []
      };

      if (primitiveOrConst == 1) {
        //var childJson = 
        parentObj.childs = this.parseTVL(valueByte);
      }
      parentJson.push(parentObj);

      if (remDataByte.length > 0) {
        if (remDataByte[0] == 0x90 && remDataByte[1] == 0x00 && remDataByte.length == 2) {
          alert("No more ");
        }
        else {
          var childJsonS = this.parseTVL(remDataByte);
          for (var c = 0; c < childJsonS.length; c++)
            parentJson.push(childJsonS[c]);
        }
      }
      return parentJson;
    }

    this.stringToByteArray = function (str) {
      var b = [], i, unicode;
      for (i = 0; i < str.length; i++) {
        unicode = str.charCodeAt(i);
        // 0x00000000 - 0x0000007f -> 0xxxxxxx
        if (unicode <= 0x7f) {
          b.push(String.fromCharCode(unicode));
          // 0x00000080 - 0x000007ff -> 110xxxxx 10xxxxxx
        } else if (unicode <= 0x7ff) {
          b.push(String.fromCharCode((unicode >> 6) | 0xc0));
          b.push(String.fromCharCode((unicode & 0x3F) | 0x80));
          // 0x00000800 - 0x0000ffff -> 1110xxxx 10xxxxxx 10xxxxxx
        } else if (unicode <= 0xffff) {
          b.push(String.fromCharCode((unicode >> 12) | 0xe0));
          b.push(String.fromCharCode(((unicode >> 6) & 0x3f) | 0x80));
          b.push(String.fromCharCode((unicode & 0x3f) | 0x80));
          // 0x00010000 - 0x001fffff -> 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
        } else {
          b.push(String.fromCharCode((unicode >> 18) | 0xf0));
          b.push(String.fromCharCode(((unicode >> 12) & 0x3f) | 0x80));
          b.push(String.fromCharCode(((unicode >> 6) & 0x3f) | 0x80));
          b.push(String.fromCharCode((unicode & 0x3f) | 0x80));
        }
      }

      return b;
    }

    this.hexToBytes = function (inpStrArr) {
      var bytes = new Uint8Array(inpStrArr.length / 2);
      j = 0;
      for (var i = 0; i < inpStrArr.length; i += 2) {
        bytes[j++] = this.hexToByte(inpStrArr[i]) << 4 | this.hexToByte(inpStrArr[i + 1]);
      }
      return bytes;
    }

    this.doTlv = function (val) {
      var retVal = val;
      var inpStrArr = this.stringToByteArray(val);
      var respJson = [];
      var j = 0;

      var pattern = /[0-9A-Fa-f]/i;
      if (pattern.test(inpStrArr)) {
        if (inpStrArr.length % 2 == 0) {
          var inpBytes = hexToBytes(inpStrArr);
          respJson = parseTVL(inpBytes);
        }
      }
      else {
        respJson = [{ "error": "invalid hex bytes" }]
      }

      return respJson;

    }


    this.getLengthVal = function (inp) {
      var retVal = 0;
      if (inp.length == 1) {
        retVal = inp[0]
      }
      else {
        for (var i = inp.length; i >= 0; i--) {
          inp[i - 1] = inp[i - 1] & 0x7F;
          retVal = retVal + (inp[i - 1] << (inp.length - i) * 7);
        }
      }
      return retVal;
    }
    this.hexArrToString = function (hexArr) {
      var hexStr = "";
      for (var i = 0; i < hexArr.length; i++) {
        hexStr += hexArr[i];
      }

      return hexStr;
    }

    this.hexToBytes = function (inpStrArr) {
      var bytes = new Uint8Array(inpStrArr.length / 2);
      var j = 0;
      for (var i = 0; i < inpStrArr.length; i += 2) {
        bytes[j++] = this.hexToByte(inpStrArr[i]) << 4 | this.hexToByte(inpStrArr[i + 1]);
      }

      return bytes;
    }
    this.h2d = function (h) { return parseInt(h, 16); }
    this.hexToByte = function (inpChar) {
      return this.h2d(inpChar);
    }



    this.pareseTvlToSchema = function (jsonObj) {
      var schemaJson = [];
      var j = 0;
      var NeedEmptyChilds = true;
      // alert(jsonObj.length);
      var tempJsonstr = '[{';
      for (var i = 0; i < jsonObj.length; i++) {

        var tag = jsonObj[i]["tag"];
        var valueKey = this.getDataType(tag);
        var value = jsonObj[i]["value"];
        var key = jsonObj[i]["tagDescr"];
        var childs = jsonObj[i]["childs"];
        var ascii = jsonObj[i]["ASCII"];
        if (valueKey.toUpperCase() == "ASCII") {
          value = ascii;
        }

        if (tag == "e1" || tag == "E1") {
          NeedEmptyChilds = false;
          var childJson = this.pareseTvlToSchema(childs);
          // key = "childs";
          //schemaJson.push({'childs':childJson});
          if (j == 0) {
            tempJsonstr += ' "' + key + '" : ' + JSON.stringify(childJson) + '';
          }
          else {
            tempJsonstr += ', "' + key + '" : ' + JSON.stringify(childJson) + '';

          }
          j++;
          //schemaJson["childs"]=$scope.pareseTvlToSchema(childs);
        }
        else {

          //alert( key +" " +value);
          if (j == 0) {
            tempJsonstr += ' "' + key + '" : "' + value + '"';
          }
          else {
            tempJsonstr += ', "' + key + '" : "' + value + '"';
          }
          j++;

        }
      }
      if (NeedEmptyChilds == true) {
        if (j == 0) {
          tempJsonstr += " 'childs':[]";
        }
        else {
          tempJsonstr += ", 'childs':[]";
        }

      }

      tempJsonstr += "}]";
      var tempJson = eval(tempJsonstr);

      schemaJson.push(tempJson[0]);
      return schemaJson;
    }

    this.GenHtmlTemplateFromSJson = function (jsonSchema, value, mode) {

      var USS = require("ufi.core").USS;
      var ufiframegen = require("ufi.frameGen");
      var ufivalidate = require("ufi.validate");
      var $ = require("jquery");
      var us = new ufiframegen.FG();
      var schemaJson = eval('[{"group":"USS","name":"basicDet","label":"Basic Details","task":"EA","desc":"","htmlType":"PAGE","entitle":"NONREADONLY", maxCol:2, col: 1,"enttlname":"","mndf":"N","dataType":"PAGE","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"0","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[{"group":"USS","name":"name","label":"Name ","task":"NONE","desc":"","htmlType":"TEXT","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"name1","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"0","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"bodyType","label":"Body Type","task":"NONE","desc":"","htmlType":"LIST","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"NONE","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"NONE|None|SLIM|Slim|AVERAGE|Average|ATHLETIC|Athletic|HEAVY|Heavy ","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"complexion","label":"complexion","task":"NONE","desc":"","htmlType":"LIST","entitle":"NONREADONLY","enttlname":"","mndf":"N","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"NONE|None|VFAIR|Very Fair|FAIR|Fair |WHEATISH|Wheatish|BWHEATISH|Wheatish |BROWN|brown|DARK|Dark","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"age","label":"Age ","task":"NONE","desc":"","htmlType":"TEXT","entitle":"NONREADONLY","enttlname":"","mndf":"N","dataType":"NUMBER","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"0","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"dob","label":"Date of Birth","task":"NONE","desc":"","htmlType":"DATE","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"DATE","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"DD/MM/YYYY or DD/MON/YYYY","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"0","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"phyStaus","label":"Physical Status ","task":"NONE","desc":"","htmlType":"LIST","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"NONE","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"NONE|None|NORMAL|Normal|PHYSICALLYCHALLENGED|Physically challenged","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"height","label":"Height ","task":"NONE","desc":"","htmlType":"TEXT","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"0","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"weight ","label":"Weight ","task":"NONE","desc":"","htmlType":"TEXT","entitle":"NONREADONLY","enttlname":"","mndf":"N","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"0","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"motherTongue","label":"Mother Tongue ","task":"NONE","desc":"","htmlType":"DIV","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"DIV","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"0","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"maritalStaus","label":"Marital Status ","task":"NONE","desc":"","htmlType":"LIST","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"NONE|None|U|Unmarried|NM|Never married","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"eatingHabits","label":"Eating Habits ","task":"NONE","desc":"","htmlType":"LIST","entitle":"NONREADONLY","enttlname":"","mndf":"N","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"NONE|None|NV|Non Vegetarian|V|Vegetarian","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"drinkingHabits","label":"Drinking Habits ","task":"NONE","desc":"","htmlType":"LIST","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"NONE","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"NONE|None|ND|Non-drinker|D|Drinker","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]},{"group":"USS","name":"smokingHabits","label":"Smoking Habits ","task":"NONE","desc":"","htmlType":"LIST","entitle":"NONREADONLY","enttlname":"","mndf":"Y","dataType":"VARCHAR","cclass":"ctable","parent":"","parentHtmlType":"","validate":"","dflt":"NONE","min":"0","max":"60","tips":"","onkeyup":"onKeyUp(this);","onchange":"onChange(this);","onkeydown":"onKeyDown(this);","onkeypress":"onKeyPress(this);","onclick":"onClick(this);","onblure":"onBlure(this);","listVal":"NONE|None|NS|Non-smoker|S|Smoker","help":"N","helpLink":"helpload","xml":"Y","xmlname":"","Xpath":"/","maxCol":"1","col":"0","childs":[]}]}]');

      var result = $.ajax({
        type: "GET",
        url: "/pillar/api/schema/" + jsonSchema + ".sjson",
        cache: false,
        async: false
      }).responseText;
      
      schemaJson = eval(result);//genObj[0].childs;
      GenHtmlTemplateFromSJson = USS;

      var usResource = eval("[{" + us.frameGenerationResoure(schemaJson[0].childs,
        schemaJson[0]
      ) + "}]");

      var usListVal = eval("[{" + us.frameGenerationListVal(schemaJson[0].childs,
        schemaJson[0]
      ) + "}]");

      var OutJson = eval("[{" + us.frameGenerationJson(schemaJson[0].childs,
        schemaJson[0]
      ) + "}]");

      var func = value;
      //alert(func);
      var inpUsListVal = eval("usListVal[0]." + schemaJson[0].name);
      var inpUsResource = eval("usResource[0]." + schemaJson[0].name);
      var inpOutJson = eval("OutJson[0]." + schemaJson[0].name);
      var ussScript = us.frameGeneration(inpUsListVal
        , inpUsResource
        , inpOutJson
        , schemaJson[0].childs
        , schemaJson[0]
        , 0
        , func
        , 0
        , mode
      );


      ussScript += "return USSContainer0";
      console.log('-------------ussScript------------');
      console.log(ussScript);

      // try
      {
        var dynFGCall = (new Function("return function(us) {" + ussScript + "};"))();

        var appendObj = dynFGCall(us);

        console.log('appendObj.innerHTML');
        console.log(appendObj.innerHTML);

        //return "<div> thsis dashboard from heaerieUssServiceProvider </div>";
        return "<div class='pageLayout'> <div class='bcontainer'>" + appendObj.innerHTML + "</div></div>";

      }
      /*  catch(e)
        {
          alert('Core App:' + e);
        }
    */
    };

    this.$get = ["apiToken", function heaerieUssServiceFactory(apiToken) {

      // let's assume that the heaerieUssService constructor was also changed to
      // accept and use the GenHtmlTemplateFromSJson argument
      return new heaerieUssService(apiToken, GenHtmlTemplateFromSJson);
    }];

  };


encodeSchemaToTvl=function(schemaJson)
{

  var Tag="";
  var Value="";
  var valueHex="";

  var rtStr="";


  var Len=0;
  for(var i=0; i< schemaJson.length; i++)
  {

//$scope.getTag
   for( key in schemaJson[i])
    {
        if ( key == "childs")
        {
          if(schemaJson[i].childs.length !=0)
          {
          var value1=$scope.encodeSchemaToTvl(schemaJson[i].childs);
          //rtStr += "E1" +  intToHexString(value1.length/2) + value1;
          rtStr +=  value1;


          }
        }
        else
        {
          Tag       =$scope.getTag(key);
          Value     =schemaJson[i][key];
          valueHex  =$scope.stringToHexStr(Value);
          Len       =valueHex.length/2;
          tagLen    = $scope.intToHexString(Len);

          rtStr += Tag + tagLen + valueHex;
        
        }
    }
    
  }

return  "E1" +intToHexString(rtStr.length/2) + rtStr;
}
