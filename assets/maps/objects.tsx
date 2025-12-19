<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.11.2" name="objects" tilewidth="80" tileheight="112" tilecount="6" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
  </properties>
  <image source="objects/chest.png" width="16" height="16"/>
 </tile>
 <tile id="1" type="Prop">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
  </properties>
  <image source="objects/house.png" width="80" height="112"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="9.33333" y="33.6667" width="62" height="71.6667"/>
  </objectgroup>
 </tile>
 <tile id="2" type="Prop">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
  </properties>
  <image source="objects/oak_tree.png" width="41" height="63"/>
  <objectgroup draworder="index" id="2">
   <object id="3" x="26.375" y="42.25">
    <polygon points="0,0 -10.375,0.125 -11,5.625 -14,11 -6.25,13.375 3.125,10.625 -0.125,5.75"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="3" type="GameObject">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="speed" type="float" value="4"/>
  </properties>
  <image source="objects/player.png" width="32" height="32"/>
  <objectgroup draworder="index" id="5">
   <object id="6" x="9.54545" y="18" width="11.9091" height="4.90909">
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="4">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
  </properties>
  <image source="objects/training_dummy.png" width="32" height="32"/>
 </tile>
 <tile id="5">
  <properties>
   <property name="atlasAsset" value="OBJECTS"/>
  </properties>
  <image source="objects/trap.png" width="16" height="16"/>
 </tile>
</tileset>
