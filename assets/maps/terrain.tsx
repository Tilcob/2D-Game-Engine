<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.11.2" name="terrain" tilewidth="16" tileheight="16" spacing="16" margin="8" tilecount="192" columns="12">
 <image source="tileset.png" width="384" height="512"/>
 <tile id="0">
  <objectgroup draworder="index" id="2">
   <object id="1" x="15.9375" y="5.9375">
    <polygon points="0,0 -7.9375,3 -11,10 -4,10.0625 0.0625,6.9375"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="5.45455" width="16" height="8"/>
  </objectgroup>
 </tile>
 <tile id="2">
  <objectgroup draworder="index" id="2">
   <object id="1" x="-0.125" y="6.9375">
    <polygon points="0,0 0.0625,5.875 4.1875,9 11,9.0625 8.4375,1.5625"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="8">
  <animation>
   <frame tileid="8" duration="200"/>
   <frame tileid="9" duration="200"/>
   <frame tileid="10" duration="200"/>
   <frame tileid="11" duration="200"/>
  </animation>
 </tile>
 <tile id="12">
  <objectgroup draworder="index" id="2">
   <object id="1" x="5.45455" y="0" width="6.54545" height="16"/>
  </objectgroup>
 </tile>
 <tile id="14">
  <objectgroup draworder="index" id="2">
   <object id="1" x="4" y="0" width="6.36364" height="16"/>
  </objectgroup>
 </tile>
 <tile id="24">
  <objectgroup draworder="index" id="2">
   <object id="1" x="5.0625" y="0.125">
    <polygon points="0,0 2.875,6.8125 11,9.9375 11,2.875 6.9375,-0.0625"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="25">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="3.63636" width="16" height="6.90909"/>
  </objectgroup>
 </tile>
 <tile id="26">
  <objectgroup draworder="index" id="2">
   <object id="3" x="4.0625" y="-0.0625">
    <polygon points="0,0 -4.0625,3.125 -4.125,9 -0.125,8.9375 4.9375,6.1875 7,0.0625"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="63">
  <animation>
   <frame tileid="63" duration="200"/>
   <frame tileid="64" duration="200"/>
   <frame tileid="65" duration="200"/>
  </animation>
 </tile>
 <wangsets>
  <wangset name="Grasscliff" type="corner" tile="-1">
   <wangcolor name="grass" color="#ff0000" tile="-1" probability="1"/>
   <wangcolor name="cliff" color="#00ff00" tile="-1" probability="1"/>
   <wangtile tileid="0" wangid="0,1,0,2,0,1,0,1"/>
   <wangtile tileid="1" wangid="0,1,0,2,0,2,0,1"/>
   <wangtile tileid="2" wangid="0,1,0,1,0,2,0,1"/>
   <wangtile tileid="12" wangid="0,2,0,2,0,1,0,1"/>
   <wangtile tileid="13" wangid="0,1,0,1,0,1,0,1"/>
   <wangtile tileid="14" wangid="0,1,0,1,0,2,0,2"/>
   <wangtile tileid="24" wangid="0,2,0,1,0,1,0,1"/>
   <wangtile tileid="25" wangid="0,2,0,1,0,1,0,2"/>
   <wangtile tileid="26" wangid="0,1,0,1,0,1,0,2"/>
   <wangtile tileid="36" wangid="0,2,0,2,0,2,0,1"/>
   <wangtile tileid="37" wangid="0,1,0,2,0,2,0,2"/>
   <wangtile tileid="48" wangid="0,2,0,2,0,1,0,2"/>
   <wangtile tileid="49" wangid="0,2,0,1,0,2,0,2"/>
   <wangtile tileid="181" wangid="0,1,0,1,0,1,0,1"/>
  </wangset>
 </wangsets>
</tileset>
