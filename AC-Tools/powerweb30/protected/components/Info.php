<?php







class Info extends Controller {

	// Levels
	function lvl($var) {
		if ($var <= '400') return '1';
		elseif ($var <= '1433') return '2';
		elseif ($var <= '3820') return '3';
		elseif ($var <= '9054') return '4';
		elseif ($var <= '17655') return '5';
		elseif ($var <= '30978') return '6';
		elseif ($var <= '52010') return '7';
		elseif ($var <= '82982') return '8';
		elseif ($var <= '126069') return '9';
		elseif ($var <= '182252') return '10';
		elseif ($var <= '260622') return '11';
		elseif ($var <= '360825') return '12';
		elseif ($var <= '490331') return '13';
		elseif ($var <= '649169') return '14';
		elseif ($var <= '844378') return '15';
		elseif ($var <= '1083018') return '16';
		elseif ($var <= '1401356') return '17';
		elseif ($var <= '1808613') return '18';
		elseif ($var <= '2314771') return '19';
		elseif ($var <= '2941893') return '20';
		elseif ($var <= '3769257') return '21';
		elseif ($var <= '4811154') return '22';
		elseif ($var <= '6110198') return '23';
		elseif ($var <= '7632340') return '24';
		elseif ($var <= '9377726') return '25';
		elseif ($var <= '11395643') return '26';
		elseif ($var <= '13731725') return '27';
		elseif ($var <= '16339413') return '28';
		elseif ($var <= '19378549') return '29';
		elseif ($var <= '23252749') return '30';
		elseif ($var <= '27675843') return '31';
		elseif ($var <= '32911197') return '32';
		elseif ($var <= '39197217') return '33';
		elseif ($var <= '47420762') return '34';
		elseif ($var <= '57899684') return '35';
		elseif ($var <= '70724362') return '36';
		elseif ($var <= '87641065') return '37';
		elseif ($var <= '107088757') return '38';
		elseif ($var <= '129885732') return '39';
		elseif ($var <= '157281282') return '40';
		elseif ($var <= '189342188') return '41';
		elseif ($var <= '227003751') return '42';
		elseif ($var <= '267317400') return '43';
		elseif ($var <= '310123925') return '44';
		elseif ($var <= '355885203') return '45';
		elseif ($var <= '404893687') return '46';
		elseif ($var <= '456755353') return '47';
		elseif ($var <= '511753757') return '48';
		elseif ($var <= '570232075') return '49';
		elseif ($var <= '632338545') return '50';
		elseif ($var <= '701655822') return '51';
		elseif ($var <= '776901823') return '52';
		elseif ($var <= '857160855') return '53';
		elseif ($var <= '941190930') return '54';
		elseif ($var <= '1037885487') return '55';
		elseif ($var <= '1149277979') return '56';
		elseif ($var <= '1276707285') return '57';
		elseif ($var <= '1420814969') return '58';
		elseif ($var <= '1584752478') return '59';
		elseif ($var <= '1823962406') return '60';
		elseif ($var <= '2072828069') return '61';
		elseif ($var <= '2330507012') return '62';
		elseif ($var <= '2611618633') return '63';
		elseif ($var <= '3120186907') return '64';
		elseif ($var <= '3704748142') return '65';
		else return '65';
		/*
		<exp>2574120298</exp> <!-- Level 56 -->
        <exp>2814391735</exp> <!-- Level 57 -->
        <exp>3156513717</exp> <!-- Level 58 -->
        <exp>3492494281</exp> <!-- Level 59 -->
        <exp>3884323131</exp> <!-- Level 60 -->
		*/
	}
	
	
	// Race
	function race($var) {
		if ($var == 'ELYOS') return '<img src="'.Yii::app()->homeUrl.'/images/top/ely.png" alt="" title = "Elyos" />';
		elseif ($var == 'ASMODIANS') return '<img src="'.Yii::app()->homeUrl.'/images/top/asmo.png" alt="" title = "Asmodian" />';
		elseif ($var == 'ASMODIAN') return '<img src="'.Yii::app()->homeUrl.'/images/top/asmo.png" alt="" title = "Asmodian" />';
		else return '???';
	}
	
	
	// Race text string
	function race_text($var) {
		if ($var == 'ELYOS') return 'Elyos';
		elseif ($var == 'ASMODIANS') return 'Asmodian';
		elseif ($var == 'ASMODIAN') return 'Asmodian';
		else return '???';
	}
	
	
	// Class pictures
	function player_class($var) {
	if ($var == 'WARRIOR') return '<img src="'.Yii::app()->homeUrl.'/images/top/warrior.png" alt="" title="Warrior" />';
				elseif ($var == 'GLADIATOR') return '<img src="'.Yii::app()->homeUrl.'/images/top/gladiator.png" alt="" title="Gladiator" />';
				elseif ($var == 'TEMPLAR') return '<img src="'.Yii::app()->homeUrl.'/images/top/templar.png" alt="" title="Templar" />';
		elseif ($var == 'SCOUT') return '<img src="'.Yii::app()->homeUrl.'/images/top/scout.png" alt="" title="Scout" />';
				elseif ($var == 'ASSASSIN') return '<img src="'.Yii::app()->homeUrl.'/images/top/assassin.png" alt="" title="Assassin" />';
				elseif ($var == 'RANGER') return '<img src="'.Yii::app()->homeUrl.'/images/top/ranger.png" alt="" title="Ranger" />';
		elseif ($var == 'MAGE') return '<img src="'.Yii::app()->homeUrl.'/images/top/mage.png" alt="" title="Mage" />';
				elseif ($var == 'SORCERER') return '<img src="'.Yii::app()->homeUrl.'/images/top/sorcerer.png" alt="" title="Sorcerer" />';
				elseif ($var == 'SPIRIT_MASTER') return '<img src="'.Yii::app()->homeUrl.'/images/top/spiritmaster.png" alt="" title="Spirit Master" />';
		elseif ($var == 'PRIEST') return '<img src="'.Yii::app()->homeUrl.'/images/top/priest.png" alt="" title="Priest" />';
				elseif ($var == 'CLERIC') return '<img src="'.Yii::app()->homeUrl.'/images/top/cleric.png" alt="" title="Cleric" />';
				elseif ($var == 'CHANTER') return '<img src="'.Yii::app()->homeUrl.'/images/top/chanter.png" alt="" title="Chanter" />';
		elseif ($var == 'ENGINEER') return '<img src="'.Yii::app()->homeUrl.'/images/top/engineer.png" alt="" title="Technist" />';
				elseif ($var == 'GUNNER') return '<img src="'.Yii::app()->homeUrl.'/images/top/gunslinger.png" alt="" title="Gunslinger" />';
		         	elseif ($var == 'RIDER') return '<img src="'.Yii::app()->homeUrl.'/images/top/aethertech.png" alt="" title="Aethertech" />';
		elseif ($var == 'ARTIST') return '<img src="'.Yii::app()->homeUrl.'/images/top/artist.png" alt="" title="Artist" />';
				elseif ($var == 'BARD') return '<img src="'.Yii::app()->homeUrl.'/images/top/songweaver.png" alt="" title="Songweaver" />';
				
		else return '???';
	}
	
	
	// Class text strings
	function class_text($var) {
	    	if ($var == 'WARRIOR') return 'Warrior';
		elseif ($var == 'GLADIATOR') return 'Gladiator';
		elseif ($var == 'TEMPLAR') return 'Templar';
		elseif ($var == 'SCOUT') return 'Scout';
		elseif ($var == 'ASSASSIN') return 'Assassin';
		elseif ($var == 'RANGER') return 'Ranger';
		elseif ($var == 'MAGE') return 'Mage';
		elseif ($var == 'SORCERER') return 'Sorcerer';
		elseif ($var == 'SPIRIT_MASTER') return 'Spirit Master';
		elseif ($var == 'PRIEST') return 'Priest';
		elseif ($var == 'CLERIC') return 'Cleric';
		elseif ($var == 'CHANTER') return 'Chanter';
		elseif ($var == 'ENGINEER') return 'Engineer';
		elseif ($var == 'GUNNER') return 'Gunner';
		elseif ($var == 'RIDER') return 'Rider';
		elseif ($var == 'ARTIST') return 'Artist';
		elseif ($var == 'BARD') return 'Bard';
		
		else return '???';
	}
	
	
	// Gender images (get your head out of the gutter - fallenfate)
	function gender($var) {
		if ($var == 'MALE') return '<img src="'.Yii::app()->homeUrl.'/images/top/male.png" alt="" title="Male" />';
		elseif ($var == 'FEMALE') return '<img src="'.Yii::app()->homeUrl.'/images/top/female.png" alt="" title="Female" />';
		else return '???';
	}
	
	
	// Server online status
	function online($var) {
		if ($var == 0) return '<img src="'.Yii::app()->homeUrl.'/images/top/off.png" alt="" title="OFFLINE" />';
		elseif ($var == 1) return '<img src="'.Yii::app()->homeUrl.'/images/top/on.png" alt="" title="ONLINE" />';
		else return '???';
	}
	
	
	// Avatar
	function face($var1, $var2) {
		if ($var1 == 'ASMODIANS' AND $var2 == 'MALE') return '<img class="table_player_img" src="'.Yii::app()->homeUrl.'/images/top/face_10.jpg" alt="" />';
		elseif ($var1 == 'ASMODIANS' AND $var2 == 'FEMALE') return '<img class="table_player_img" src="'.Yii::app()->homeUrl.'/images/top/face_11.jpg" alt="" />';
		elseif ($var1 == 'ASMODIAN' AND $var2 == 'MALE') return '<img class="table_player_img" src="'.Yii::app()->homeUrl.'/images/top/face_10.jpg" alt="" />';
		elseif ($var1 == 'ASMODIAN' AND $var2 == 'FEMALE') return '<img class="table_player_img" src="'.Yii::app()->homeUrl.'/images/top/face_11.jpg" alt="" />';
		elseif ($var1 == 'ELYOS' AND $var2 == 'MALE') return '<img class="table_player_img" src="'.Yii::app()->homeUrl.'/images/top/face_00.jpg" alt="" />';
		elseif ($var1 == 'ELYOS' AND $var2 == 'FEMALE') return '<img class="table_player_img" src="'.Yii::app()->homeUrl.'/images/top/face_01.jpg" alt="" />';
		else return '???';
	}
	
	
	// Location
	function world($var) {
		if ($var == '110010000') return 'Sanctum';
		elseif ($var == '110020000') return 'Cloister Of Kaisinel';
		elseif ($var == '110070000') return 'Kaisinel Academy';
		elseif ($var == '120010000') return 'Pandaemonium';
		elseif ($var == '120020000') return 'Convent Of Marchutan';
		elseif ($var == '130090000') return 'Wisplight Abbey';
		elseif ($var == '140010000') return 'Fatebound Abbey';
		elseif ($var == '120080000') return 'Marchutan Priory';
		elseif ($var == '210010000') return 'Poeta';
		elseif ($var == '210020000') return 'Eltnen';
		elseif ($var == '210030000') return 'Verteron';
		elseif ($var == '210040000') return 'Heiron';
		elseif ($var == '210050000') return 'Inggison';
		elseif ($var == '210060000') return 'Theobomos';
		elseif ($var == '210070000') return 'LF5'; // check online
		elseif ($var == '210080000') return 'LF5_Ship'; // check online
		elseif ($var == '220010000') return 'Ishalgen';
		elseif ($var == '220020000') return 'Morheim';
		elseif ($var == '220030000') return 'Altgard';
		elseif ($var == '220040000') return 'Beluslan';
		elseif ($var == '220050000') return 'Brusthonin';
		elseif ($var == '220070000') return 'Gelkmaros';
		elseif ($var == '220080000') return 'DF5';
		elseif ($var == '220090000') return 'DF5_Ship';
		elseif ($var == '300010000') return 'IDAbPro';
		elseif ($var == '300020000') return 'IDTest_Dungeon';
		elseif ($var == '300030000') return 'Nochsana Training Camp';
		elseif ($var == '300040000') return 'Dark Poeta';
		elseif ($var == '300050000') return 'Asteria Chamber';
		elseif ($var == '300060000') return 'Sulfur Tree Nest';  
		elseif ($var == '300070000') return 'Chamber Of Roah';
		elseif ($var == '300080000') return 'Left Wing Chamber';  
		elseif ($var == '300090000') return 'Right Wing Chamber';
		elseif ($var == '300100000') return 'Steel Rake';
		elseif ($var == '300110000') return 'Dredgion';
		elseif ($var == '300120000') return 'Kysis Chamber';   
		elseif ($var == '300130000') return 'Miren Chamber';   
		elseif ($var == '300140000') return 'Krotan Chamber';    
		elseif ($var == '300150000') return 'Udas Temple';     
		elseif ($var == '300160000') return 'Lower Udas Temple';    
		elseif ($var == '300170000') return 'Beshmundir Temple';    
		elseif ($var == '300190000') return 'Taloc\'s Hollow';    
		elseif ($var == '300200000') return 'Haramel';     
		elseif ($var == '300210000') return 'Chantra Dredgion'; 
		elseif ($var == '300220000') return 'Abyssal Splinter';   
		elseif ($var == '300230000') return 'Kromede Trial';     
		elseif ($var == '300240000') return 'Aturam Sky Fortress'; 
		elseif ($var == '300250000') return 'Esoterrace';
		elseif ($var == '300260000') return 'Elementis Forest';
		elseif ($var == '300270000') return 'Argent Manor';
		elseif ($var == '300280000') return 'Rentus Base'; 
		elseif ($var == '300290000') return 'Test_MRT_IDZone';
		elseif ($var == '300300000') return 'Empyrean Crucible';   
		elseif ($var == '300310000') return 'Raksang';     
		elseif ($var == '300320000') return 'Crucible Challenge';    
		elseif ($var == '300330000') return 'Protector\'s Realm';    
		elseif ($var == '300350000') return 'Arena of Chaos';     
		elseif ($var == '300360000') return 'Arena of Discipline';  
		elseif ($var == '300380000') return 'Muada\'s Trencher';    
		elseif ($var == '300390000') return 'Israphel\'s Tract';    
		elseif ($var == '300400000') return 'Tiamaranta\'s Eye';     
		elseif ($var == '300410000') return 'Griffoen';     
		elseif ($var == '300420000') return 'Chaos Training Grounds'; 
		elseif ($var == '300430000') return 'Discipline Training Grounds';
		elseif ($var == '300440000') return 'Terath Dredgion';   
		elseif ($var == '300450000') return 'Arena of Harmony';    
		elseif ($var == '300460000') return 'Steel Rake Cabin';     
		elseif ($var == '300470000') return 'Satra Treasure Hoard';  
		elseif ($var == '300480000') return 'Danuar Mysticarium'; 
		elseif ($var == '300490000') return 'Throne of Blood';   
		elseif ($var == '300500000') return 'Extradimensional Void';   
		elseif ($var == '300510000') return 'Tiamat Stronghold';   
		elseif ($var == '300520000') return 'Dragon Lords Refuge';   
		elseif ($var == '300530000') return 'Idgel Research Center';
		elseif ($var == '300540000') return 'The Eternal Bastion'; 
		elseif ($var == '300550000') return 'Arena of Glory';     
		elseif ($var == '300560000') return 'Shugo Imperial Tomb';  
		elseif ($var == '300570000') return 'Harmony Training Grounds';
		elseif ($var == '300580000') return 'Void Cube';
		elseif ($var == '300590000') return 'Ophidan Bridge';
		elseif ($var == '300600000') return 'Unstable Splinterpath'; 
		elseif ($var == '300700000') return 'The Hexway';     
		elseif ($var == '300800000') return 'Infinity Shard'; 
		elseif ($var == '300900000') return 'Danuar Infinity Shard';
		elseif ($var == '301000000') return 'Idgel Storage'; 
		elseif ($var == '301010000') return 'Steel Rose Cargo (Solo)';
		elseif ($var == '301020000') return 'Steel Rose Quarters (Solo)';
		elseif ($var == '301030000') return 'Steel Rose Cargo (Group)'; 
		elseif ($var == '301040000') return 'Steel Rose Quarters (Group)';
		elseif ($var == '301050000') return 'Steel Rose Deck'; 
		elseif ($var == '301100000') return 'Unity Training Grounds';   
		elseif ($var == '301110000') return 'Danuar Reliquary'; 
		elseif ($var == '301120000') return 'Kamar Battlefield';
		elseif ($var == '301130000') return 'Sauro Supply Base';
		elseif ($var == '301140000') return 'Danuar Sanctuary';
		elseif ($var == '301150000') return 'Rumakiki\'s Conspiracy (Solo)';
		elseif ($var == '301160000') return 'Nightmare Circus'; 
		elseif ($var == '301170000') return 'Legion\'s Idgel Research Center';
		elseif ($var == '301180000') return 'Legion\'s Void Cube'; 
		elseif ($var == '301190000') return 'Legion\'s Danuar Mysticarium'; 
		elseif ($var == '301200000') return 'The Nightmare Circus'; 
		elseif ($var == '301210000') return 'Engulfed Ophidan Bridge';
		elseif ($var == '301220000') return 'Iron Wall Warfront'; 
		elseif ($var == '301230000') return 'Illuminary Obelisk';     
		elseif ($var == '301240000') return 'Legion\'s Kysis Barracks'; 
		elseif ($var == '301250000') return 'Legion\'s Miren Barracks';
		elseif ($var == '301260000') return 'Legion\'s Krotan Barracks';   
		elseif ($var == '301270000') return 'Linkgate Foundry'; 
		elseif ($var == '301280000') return 'Kysis Barracks';
		elseif ($var == '301290000') return 'Miren Barracks'; 
		elseif ($var == '301300000') return 'Krotan Barracks';
		elseif ($var == '301310000') return 'Idgel Dome';
		elseif ($var == '301320000') return 'Lucky Ophidan Bridge';     
		elseif ($var == '301330000') return 'Lucky Danuar Reliquary';     
		elseif ($var == '301340000') return 'Linkgate Foundry Legion';     
		elseif ($var == '301360000') return 'Infernal Danuar Reliquary';     
		elseif ($var == '301370000') return 'Infernal Illuminary Obelisk';     
		elseif ($var == '301380000') return 'IDLDF5_Under_02_e';
		elseif ($var == '301390000') return 'IDSeal'; 
		elseif ($var == '301400000') return 'The Shugo Emperor\'s Vault';     
		elseif ($var == '301500000') return 'IDLegion'; 
		elseif ($var == '310010000') return 'Karamatis A';
		elseif ($var == '310020000') return 'Karamatis B';  
		elseif ($var == '310030000') return 'Aerdina';  
		elseif ($var == '310040000') return 'Geranaia';  
		elseif ($var == '310050000') return 'Aetherogenetics Lab'; 
		elseif ($var == '310060000') return 'Sliver of Darkness'; 
		elseif ($var == '310070000') return 'Sliver of Darkness Stigma';
		elseif ($var == '310080000') return 'Sanctum Underground Arena';
		elseif ($var == '310090000') return 'Indratu Fortress';  
		elseif ($var == '310100000') return 'Azoturan Fortress';  
		elseif ($var == '310110000') return 'Theobomos Lab';   
		elseif ($var == '310120000') return 'Ataxiar A';   
		elseif ($var == '320010000') return 'Ataxiar B';
		elseif ($var == '320020000') return 'Ataxiar C';   
		elseif ($var == '320030000') return 'Bregirun';    
		elseif ($var == '320040000') return 'Nidalber';   
		elseif ($var == '320050000') return 'Sky Temple Interior';  
		elseif ($var == '320060000') return 'Space of Oblivion';
		elseif ($var == '320070000') return 'Space of Destiny';
		elseif ($var == '320080000') return 'Draupnir Cave';
		elseif ($var == '320090000') return 'Triniel Underground Arena';
		elseif ($var == '320100000') return 'Fire Temple';    
		elseif ($var == '320110000') return 'Alquimia Research Center';
		elseif ($var == '320120000') return 'Shadow Court Dungeon';  
		elseif ($var == '320130000') return 'Adma Stronghold'; 
		elseif ($var == '320140000') return 'Ataxiar D';
		elseif ($var == '320150000') return 'Padmarashka\'s Cave';  
		elseif ($var == '330010010') return 'Vein of Flame';
		elseif ($var == '400010000') return 'Reshanta';
		elseif ($var == '400020000') return 'Belus';
		elseif ($var == '400030000') return 'Transidium Annex';
		elseif ($var == '400040000') return 'Aspida';
		elseif ($var == '400050000') return 'Atanatos';
		elseif ($var == '400060000') return 'Disillon';
		elseif ($var == '510010000') return 'LF_Prison';
		elseif ($var == '520010000') return 'DF_Prison';
		elseif ($var == '600010000') return 'Silentera Canyon';
		elseif ($var == '600020000') return 'Sarpan';
		elseif ($var == '600030000') return 'Tiamaranta';
		elseif ($var == '600040000') return 'Tiamaranta\'s Eye';
		elseif ($var == '600050000') return 'Katalam';
		elseif ($var == '600060000') return 'Danaria';
		elseif ($var == '600070000') return 'Idian Depths';
		elseif ($var == '600080000') return 'Live Party Concert Hall';
		elseif ($var == '600090000') return 'Kaldor';
		elseif ($var == '600100000') return 'Levinshor';
		elseif ($var == '700010000') return 'Oriel';
		elseif ($var == '700020000') return 'Elysean Legion Housing';
		elseif ($var == '710010000') return 'Pernon';
		elseif ($var == '710020000') return 'Asmodian Legion Housing';
		elseif ($var == '720010000') return 'Oriel Studio';   
		elseif ($var == '730010000') return 'Pernon Studio';  
		elseif ($var == '900020000') return 'Test Basic';
		elseif ($var == '900030000') return 'Test Server';
		elseif ($var == '900100000') return 'Test GiantMonster';
		elseif ($var == '900110000') return 'Housing Barrack';
		elseif ($var == '900120000') return 'Test Empyrean Crucible';
		elseif ($var == '900130000') return 'IDLDF5RE Test';
		elseif ($var == '900140000') return 'Test Kgw';
		elseif ($var == '900150000') return 'Test Basic Mj';
		elseif ($var == '900170000') return 'Test Intro';
		elseif ($var == '900180000') return 'Test Server Art';
		elseif ($var == '900190000') return 'Test Tag Match';
		elseif ($var == '900200000') return 'test_timeattack';
		elseif ($var == '900220000') return 'System_Basic';
		else return $var;
	}
	
	
	// Titles
	function title($var) {
		if ($var == '-1') return '---';
		elseif ($var == '1') return 'Poeta\'s Protector'; // Elyos
		elseif ($var == '2') return 'Verteron\'s Warrior'; // Elyos
		elseif ($var == '3') return 'Bottled Lightning'; // Elyos
		elseif ($var == '4') return 'Tree Hugger'; // Elyos
		elseif ($var == '5') return 'Krall Hunter'; // Elyos
		elseif ($var == '6') return 'Straw for Brains'; // Elyos
		elseif ($var == '7') return 'Animal Lover'; // Elyos
		elseif ($var == '8') return 'Krall Whisperer'; // Elyos
		elseif ($var == '9') return 'Patient'; // Elyos
		elseif ($var == '10') return 'Mabangtah\'s Envoy'; // Elyos
		elseif ($var == '11') return 'Demolition\'s Expert'; // Elyos
		elseif ($var == '12') return 'Eltnen\'s Hero'; // Elyos
		elseif ($var == '13') return 'Klaw Hunter'; // Elyos
		elseif ($var == '14') return 'Aerialist'; // Elyos
		elseif ($var == '15') return 'Kobold Chef'; // Elyos
		elseif ($var == '16') return 'Isson\'s Apologist'; // Elyos
		elseif ($var == '17') return 'Eulogist'; // Elyos
		elseif ($var == '18') return 'Bloodsworn'; // Elyos
		elseif ($var == '19') return 'Veritas Agent'; // Elyos
		elseif ($var == '20') return 'Savior of Eiron Forest'; // Elyos
		elseif ($var == '21') return 'Gestanerk\'s Avenger'; // Elyos
		elseif ($var == '22') return 'Bounty Hunter'; // Elyos
		elseif ($var == '23') return 'Arbolu\'s Anointed'; // Elyos
		elseif ($var == '24') return 'Chief Investigator'; // Elyos
		elseif ($var == '25') return 'Indratu Bane'; // Elyos
		elseif ($var == '26') return 'Big Damn Hero'; // Elyos
		elseif ($var == '27') return 'Not-Quite-Master of Disguise'; // Elyos
		elseif ($var == '28') return 'Master Angler'; // Elyos
		elseif ($var == '29') return 'Spymaster'; // Elyos
		elseif ($var == '30') return 'Balaur Whisperer'; // Elyos
		elseif ($var == '31') return 'Tough'; // Elyos
		elseif ($var == '32') return 'Battle-Hardened'; // Elyos
		elseif ($var == '33') return 'Invincible'; // Elyos
		elseif ($var == '34') return 'Heroic'; // Elyos
		elseif ($var == '35') return 'Dread Pirate'; // Elyos
		elseif ($var == '36') return 'Top Expert'; // Elyos
		elseif ($var == '37') return 'Miragent Holy Templar'; // Elyos
		elseif ($var == '38') return 'Aetheric Master'; // Elyos
		elseif ($var == '39') return 'Daeva of Mercy'; // Elyos
		elseif ($var == '40') return 'Dragon Sword Master'; // Elyos
		elseif ($var == '41') return 'Honorary Black Cloud'; // Elyos
		elseif ($var == '42') return 'Krall Stalker'; // Elyos
		elseif ($var == '43') return 'Battering Ram'; // Elyos
		elseif ($var == '44') return 'Tenacious Pursuer'; // Elyos
		elseif ($var == '45') return 'Gullible'; // Elyos
		elseif ($var == '46') return 'Traitor\'s Bane'; // Elyos
		elseif ($var == '47') return 'Drakanhammer'; // Elyos
		elseif ($var == '48') return 'Knight Errant'; // Elyos
		elseif ($var == '49') return 'Seraphic Vindicator'; // Elyos
		elseif ($var == '50') return 'Dark Sovereign'; // Elyos
		elseif ($var == '51') return 'Raider Hero'; // Asmodian
		elseif ($var == '52') return 'Treasure Hunter'; // Asmodian
		elseif ($var == '53') return 'Mosbear Slayer'; // Asmodian
		elseif ($var == '54') return 'Mau Whisperer'; // Asmodian
		elseif ($var == '55') return 'Kind'; // Asmodian
		elseif ($var == '56') return 'Legendary Hunter'; // Asmodian
		elseif ($var == '57') return 'Protector of Altgard'; // Asmodian
		elseif ($var == '58') return 'Tayga Slayer'; // Asmodian
		elseif ($var == '59') return 'Curse Breaker'; // Asmodian
		elseif ($var == '60') return 'Protector of Morheim'; // Asmodian
		elseif ($var == '61') return 'Shugo Chef'; // Asmodian
		elseif ($var == '62') return 'Ginseng-Infused'; // Asmodian
		elseif ($var == '63') return 'Honorary Kidorun'; // Asmodian
		elseif ($var == '64') return 'Shedim Altruist'; // Asmodian
		elseif ($var == '65') return 'Mosbear Nemesis'; // Asmodian
		elseif ($var == '66') return 'Silver Mane Ally'; // Asmodian
		elseif ($var == '67') return 'Postal'; // Asmodian
		elseif ($var == '68') return 'Provocateur'; // Asmodian
		elseif ($var == '69') return 'Tenacious'; // Asmodian
		elseif ($var == '70') return 'The Cat\'s Meow'; // Asmodian
		elseif ($var == '71') return 'Unyielding Pioneer'; // Asmodian
		elseif ($var == '72') return 'Protector of Brusthonin'; // Asmodian
		elseif ($var == '73') return 'Easy Mark'; // Asmodian
		elseif ($var == '74') return 'Beluslan\'s Hero'; // Asmodian
		elseif ($var == '75') return 'Snowfield Protector'; // Asmodian
		elseif ($var == '76') return 'Besfer\'s Shield'; // Asmodian
		elseif ($var == '77') return 'Scourge of Mt. Musphel'; // Asmodian
		elseif ($var == '78') return 'Loremaster'; // Asmodian
		elseif ($var == '79') return 'Emissary'; // Asmodian
		elseif ($var == '80') return 'Balaur Whisperer'; // Asmodian
		elseif ($var == '81') return 'Tough'; // Asmodian
		elseif ($var == '82') return 'Battle-Hardened'; // Asmodian
		elseif ($var == '83') return 'Invincible'; // Asmodian
		elseif ($var == '84') return 'Heroic'; // Asmodian
		elseif ($var == '85') return 'Steel Rake Headhunter'; // Asmodian
		elseif ($var == '86') return 'Top Expert'; // Asmodian
		elseif ($var == '87') return 'Fenris\'s Fang';
		elseif ($var == '88') return 'Aetheric Master'; // Asmodian
		elseif ($var == '89') return 'Azphel\'s Aegis'; // Asmodian
		elseif ($var == '90') return 'Master of Agrif\'s Rage'; // Asmodian
		elseif ($var == '91') return 'Wheeler-Dealer'; // Asmodian
		elseif ($var == '92') return 'Factotum'; // Asmodian
		elseif ($var == '93') return 'Valiant'; // Asmodian
		elseif ($var == '94') return 'Silver Mane Champion'; // Asmodian
		elseif ($var == '95') return 'Born Merchant'; // Asmodian
		elseif ($var == '96') return 'Shadow Marked'; // Asmodian
		elseif ($var == '97') return 'Spiritspeaker'; // Asmodian
		elseif ($var == '98') return 'Pirate of the Carobian'; // Asmodian
		elseif ($var == '99') return 'Shedim Conquerer'; // Asmodian
		elseif ($var == '100') return 'Dark Vindicator'; // Asmodian
		elseif ($var == '101') return '<span style="color: #4876FF;">Settler of Aion</span>'; // SHARED, Special title.
		elseif ($var == '102') return '<span style="color: #4876FF;">Destiny Ascendant</span>'; // SHARED, Special title.
		elseif ($var == '103') return '<span style="color: #4876FF;">Adept of Aion</span>'; // SHARED, Special title.
		elseif ($var == '104') return '<span style="color: #4876FF;">Shining Intellectual</span>'; // SHARED, Special title.
		elseif ($var == '105') return '<span style="color: #4876FF;">Sage of Aion</span>'; // SHARED, Special title.
		elseif ($var == '106') return '<span style="color: #4876FF;">Munificent</span>'; // SHARED, Special title.
		elseif ($var == '107') return 'Iron Stomached'; // Elyos
		elseif ($var == '108') return 'Shulack Friend'; // Elyos
		elseif ($var == '109') return 'Guardian Ally'; // Elyos
		elseif ($var == '110') return 'Kaisinel\'s Assassin'; // Elyos
		elseif ($var == '111') return 'Guardian Protector'; // Elyos
		elseif ($var == '112') return 'Balaur Eradicator'; // Elyos
		elseif ($var == '113') return 'Discoverer of the Secret'; // Elyos
		elseif ($var == '114') return '<span style="color: #FF9900;">Vanquisher of Mastarius</span>'; // Elyos, Special title.
		elseif ($var == '115') return '<span style="color: #FF9900;">Alabaster Wing</span>'; // Elyos, Special title.
		elseif ($var == '116') return '<span style="color: #FF9900;">Alabaster Eye</span>'; // Elyos, Special title.
		elseif ($var == '117') return 'Alabaster Hand'; // Elyos
		elseif ($var == '118') return 'Radiant Fist'; // Special title.
		elseif ($var == '119') return '<span style="color: #FF9900;">Radiant Shield</span>'; // Elyos, Special title.
		elseif ($var == '120') return '<span style="color: #FF9900;">Radiant Blade</span>'; // Elyos, Special title.
		elseif ($var == '121') return '<span style="color: #FF9900;">Radiant Shroud</span>'; // Elyos, Special title.
		elseif ($var == '122') return 'Seeker'; // Elyos
		elseif ($var == '123') return '<span style="color: #FF9900;">Tracker</span>'; // Elyos, Special title.
		elseif ($var == '124') return '<span style="color: #FF9900;">Hunter</span>'; // Elyos, Special title.
		elseif ($var == '125') return 'Fortuneer'; // Elyos
		elseif ($var == '126') return 'Lesson Giver'; // Asmodian
		elseif ($var == '127') return 'Incarnation of Vengeance'; // Asmodian
		elseif ($var == '128') return 'Apostle of Justice'; // Asmodian
		elseif ($var == '129') return 'Veille\'s Adversary'; // Asmodian
		elseif ($var == '130') return 'Hand of Support'; // Asmodian
		elseif ($var == '131') return 'Balaur Voltaire'; // Asmodian
		elseif ($var == '132') return 'Delver of Mysteries'; // Asmodian
		elseif ($var == '133') return 'Vanquisher of Veille</span>'; // Asmodian, Special title.
		elseif ($var == '134') return '<span style="color: #B0171F;">Field Warden</span>'; // Asmodian, Special title.
		elseif ($var == '135') return '<span style="color: #B0171F;">Field Agent</span>'; // Asmodian, Special title.
		elseif ($var == '136') return 'Field Director'; // Asmodian
		elseif ($var == '137') return '<span style="color: #B0171F;">Blood Champion</span>'; // Asmodian, Special title.
		elseif ($var == '138') return '<span style="color: #B0171F;">Blood Sentinel</span>'; // Asmodian, Special title.
		elseif ($var == '139') return '<span style="color: #B0171F;">Blood Conqueror</span>'; // Asmodian, Special title.
		elseif ($var == '140') return '<span style="color: #B0171F;">Blood Phalanx</span>'; // Asmodian, Special title.
		elseif ($var == '141') return 'Fixer'; // Asmodian
		elseif ($var == '142') return '<span style="color: #B0171F;">Operator</span>'; // Asmodian, Special title.
		elseif ($var == '143') return '<span style="color: #B0171F;">Handler</span>'; // Asmodian, Special title.
		elseif ($var == '144') return 'Daemon'; // Asmodian
		elseif ($var == '145') return '<span style="color: #4876FF;">Taegeuk Hero</span>'; // SHARED, Special title.
		elseif ($var == '146') return '<span style="color: #4876FF;">Daddy Long Legs</span>'; // SHARED, Special title.
		elseif ($var == '147') return 'Akarios\' Friend'; // Elyos
		elseif ($var == '148') return 'Arekedil\'s Hope'; // Asmodian
		elseif ($var == '149') return 'Tiamat Stalker'; // Elyos
		elseif ($var == '150') return 'Obstinate Herdsman'; // Asmodian
		elseif ($var == '151') return '<span style="color: #4876FF;">Master of Many Pets</span>'; // SHARED, Special title.
		elseif ($var == '152') return '<span style="color: #4876FF;">Charmer</span>'; // SHARED, Special title.
		elseif ($var == '153') return '<span style="color: #4876FF;">Sucker</span>'; // SHARED, Special title.
		elseif ($var == '154') return '<span style="color: #FF9900;">Circumspect Advisor</span>'; // Elyos, Special title.
		elseif ($var == '155') return '<span style="color: #FF9900;">Kind Counsel</span>'; // Elyos, Special title.
		elseif ($var == '156') return 'Key Master'; // Elyos
		elseif ($var == '157') return '<span style="color: #B0171F;">Shrewd Advisor</span>'; // Asmodian, Special title.
		elseif ($var == '158') return '<span style="color: #B0171F;">Outstanding Advisor</span>'; // Asmodian, Special title.
		elseif ($var == '159') return 'The Circle Leader'; // Asmodian
		elseif ($var == '160') return '<span style="color: #4876FF;">Gufrinerk\'s Patron</span>'; // SHARED, Special title.
		elseif ($var == '161') return '<span style="color: #4876FF;">Jielinlinerk\'s Patron</span>'; // SHARED, Special title.
		elseif ($var == '162') return '<span style="color: #4876FF;">Hot Shot</span>'; // SHARED, Special title.
		elseif ($var == '163') return '<span style="color: #B0171F;">Jielinlinerk\'s Faithful Patron</span>'; // CHECK ON ELYOS, Special title.
		elseif ($var == '164') return 'Empyrean Challenger'; // Elyos
		elseif ($var == '165') return 'Up-and-Comer'; // Elyos
		elseif ($var == '166') return 'Master of Preceptors'; // Elyos
		elseif ($var == '167') return 'Mighty Combatant'; // Elyos
		elseif ($var == '168') return 'Crucible Champion'; // Elyos
		elseif ($var == '169') return 'Empyrean Challenger'; // Asmodian
		elseif ($var == '170') return 'Up-and-Comer'; // Asmodian
		elseif ($var == '171') return 'Master of Preceptors'; // Asmodian
		elseif ($var == '172') return 'Mighty Combatant'; // Asmodian
		elseif ($var == '173') return 'Crucible Champion'; // Asmodian
		elseif ($var == '174') return 'Memory Journaler'; // Elyos
		elseif ($var == '175') return 'Dream Journaler'; // Asmodian
		elseif ($var == '176') return '<span style="color: #B0171F;">Festival Crab</span>'; // CHECK ON ELYOS, Special title.
		elseif ($var == '177') return '<span style="color: #66CD00;">[Event] Memorialist</span>'; // SHARED, Special EVENT title.
		elseif ($var == '178') return '<span style="color: #B0171F;">Indomitable</span>'; // CHECK ON ELYOS, Special title.
		elseif ($var == '179') return '<span style="color: #DC143C;">New</span> <span style="color: #00EE00;">Year</span>'; // SHARED, Special EVENT title.
		elseif ($var == '180') return '<span style="color: #B0171F;">Daevic Defender</span>'; // CHECK ON ELYOS, Special title. 
		elseif ($var == '181') return '<span style="color: #5D478B;">Self-pitying :(</span>'; // Special title.
		elseif ($var == '182') return '<span style="color: #EEC900; font-weight: bold; text-decoration: underline; text-shadow: 0px 0px 4px #FFEC8B;">Legendary</span>'; // Special title.
		elseif ($var == '183') return 'Crucible Challenger'; // Elyos
		elseif ($var == '184') return 'Crucible Challenger'; // Asmodian
		elseif ($var == '185') return '<span style="color: #B0171F;">Majordomo of Discipline</span>'; // CHECK ON ELYOS, Special title.
		elseif ($var == '186') return '<span style="color: #B0171F;">Brawny</span>'; // CHECK ON ELYOS, Special title.
		elseif ($var == '187') return '<span style="color: #9AC0CD;">Sea-Like</span>'; // Special title.
		elseif ($var == '188') return '<span style="color: #B0171F;">Novice</span>'; // CHECK ON ELYOS, Special title. 
		elseif ($var == '189') return '<span style="color: #B0171F;">Returnee</span>'; // CHECK ON ELYOS, Special title.
		elseif ($var == '190') return '<span style="color: #EE7AE9;">¤ Universally Friendly ¤</span>'; // Special title.
		elseif ($var == '191') return '<span style="color: #EE30A7; font-weight: bold; text-decoration: underline; text-shadow: 0px 0px 4px #EEAEEE;">¤ Universally Friendly ¤</span>'; // Special title.
		elseif ($var == '192') return '<span style="color: #EE4000; font-weight: bold; text-decoration: underline; text-shadow: 0px 0px 4px #FF7F50;">Tra</span><span style="color: #FF7F24; font-weight: bold; text-decoration: underline; text-shadow: 0px 0px 4px #FFA07A;">ilbla</span><span style="color: #EE4000; font-weight: bold; text-decoration: underline; text-shadow: 0px 0px 4px #FF7F50;">zer</span>'; // Special title.
		elseif ($var == '193') return '<span style="color: #7171C6;">&#9728; Friendly Sponsor &#9728;</span>'; // Special title.
		elseif ($var == '194') return '<span style="color: #71C671; text-shadow: 0px 0px 4px #71C671;">&#9734;&#9734; Earnest Sponsor &#9734;&#9734;</span>'; // Special title.
		elseif ($var == '195') return '<span style="color: #228B22; text-shadow: 0px 0px 4px #20B2AA;">&#9733;&#9733;&#9733; Diligent Sponsor &#9733;&#9733;&#9733;</span>'; // Special title.
		elseif ($var == '196') return '<span style="color: #6E8B3D; text-shadow: 0px 0px 4px #A2CD5A;">&#9755; Big Hitter</span>'; // Special title.
		elseif ($var == '197') return '<span style="color: #6E8B3D; text-shadow: 0px 0px 4px #A2CD5A;">&#9755;&#9755; Eager Hitter</span>'; // Special title.
		elseif ($var == '198') return '<span style="color: #6E8B3D; text-shadow: 0px 0px 4px #A2CD5A;">&#9755;&#9755;&#9755; Hard Hitter</span>'; // Special title.
		elseif ($var == '199') return '<span style="color: #6E8B3D; text-shadow: 0px 0px 4px #A2CD5A;">&#9755;&#9755;&#9755;&#9755; Heavy Hitter</span>'; // Special title.
		elseif ($var == '200') return '<span style="color: #8B8878; text-shadow: 0px 0px 4px #CDBA96;">&#9729;&#8593 Dreamer &#8593&#9729;</span>';
		elseif ($var == '201') return 'Love\'s Bane'; // Elyos
		elseif ($var == '202') return 'Empowered'; // Elyos
		elseif ($var == '203') return 'Breaking up the Asmodian Scout Band'; // Elyos
		elseif ($var == '204') return 'Tiamaranta\'s Eye Visitor'; // Elyos
		elseif ($var == '205') return 'Shining Spear'; // Elyos
		elseif ($var == '206') return 'Proud Homeowner'; // Elyos
		elseif ($var == '207') return 'Expert Infiltrator'; // Elyos
		elseif ($var == '208') return 'Raksang Sealbreaker'; // Elyos
		elseif ($var == '209') return 'Dream Chaser'; // Elyos
		elseif ($var == '210') return 'Davlin Rescuer'; // Elyos
		elseif ($var == '211') return 'Reian Savior'; // Elyos
		elseif ($var == '212') return 'Malodorous'; // Asmodian
		elseif ($var == '213') return 'Sealing Force'; // Asmodian
		elseif ($var == '214') return 'Breaking up the Elyos Scout Band'; // Asmodian
		elseif ($var == '215') return 'Tiamat\'s Defier'; // Asmodian
		elseif ($var == '216') return 'Shadow Spear'; // Asmodian
		elseif ($var == '217') return 'House Owner'; // Asmodian
		elseif ($var == '218') return 'Expert Undercover'; // Asmodian
		elseif ($var == '219') return 'Raksha Confronter'; // Asmodian
		elseif ($var == '220') return 'Friend of True Love'; // Asmodian
		elseif ($var == '221') return 'Honored Guest'; // Asmodian
		elseif ($var == '222') return 'Rentus\'s Savior'; // Asmodian
		elseif ($var == '223') return 'Free Spirit'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '224') return 'Prima-Daeva'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '225') return 'Tasteful Daeva'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '226') return 'Student Leader'; // SHARED
		elseif ($var == '227') return 'Stage Setting No. 1'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '228') return 'Supersonic Daeva'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '229') return 'Lightspeed Daeva'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '230') return 'Daeva in a Predicament'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '231') return 'A Wracked Daeva'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '232') return 'A Chaotic Daeva'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '233') return 'A Lone Daeva'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '234') return '<span style="color: #FFB6C1; text-shadow: 0 0 10px #FFB6C1;">Birthday Daeva</span>'; // CHECK ON ASMODIAN (don't change style), Special title.
		elseif ($var == '235') return 'Drunken Master &#9674;'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '236') return '&#9764; Crafting Association Completion Certificate &#9764;'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '237') return 'Siel\'s Warrior'; // Elyos
		elseif ($var == '238') return 'Siel\'s Fighter'; // Asmodian
		elseif ($var == '239') return 'Ten-time Champion'; // Elyos
		elseif ($var == '240') return 'Twenty-time Champion'; // Elyos
		elseif ($var == '241') return 'Thirty-time Champion'; // Elyos
		elseif ($var == '242') return 'Forty-time Champion'; // Elyos
		elseif ($var == '243') return 'Academy Legend'; // Elyos
		elseif ($var == '244') return 'Ten-time Champion'; // Asmodian
		elseif ($var == '245') return 'Twenty-time Champion'; // Asmodian
		elseif ($var == '246') return 'Thirty-time Champion'; // Asmodian
		elseif ($var == '247') return 'Forty-time Champion'; // Asmodian
		elseif ($var == '248') return 'Priory Legend'; // Asmodian
		elseif ($var == '249') return 'Tiamat\'s Challenger'; // Elyos
		elseif ($var == '250') return 'Tiamat\'s Challenger'; // Asmodian
		elseif ($var == '251') return 'Panesterra Conqueror'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '252') return 'Dragonbane'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '253') return 'Dragon Slayer'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '254') return 'The Pinnacle of Beauty'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '255') return 'Top Gun'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '256') return 'Katalam\'s Hope'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '257') return 'Katalam\'s Light'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '258') return 'Watcher of the Forest'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '259') return 'Nature\'s Friend'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '260') return 'Victor in All Realms'; // CHECK ON ASMODIAN, Special title.
		elseif ($var == '261') return 'Elemental Sage';
		elseif ($var == '262') return 'Stalwart';
		elseif ($var == '263') return 'Guardian Detachment\'s Hero'; // Elyos
		elseif ($var == '264') return 'Archon Battlegroup\'s Hero'; // Asmodian
		elseif ($var == '265') return 'Quick Feet';
		elseif ($var == '266') return 'Dexterous Agility';
		elseif ($var == '267') return 'Head and Shoulders Above the Rest';
		elseif ($var == '268') return 'Aion\'s Chosen';
		elseif ($var == '269') return 'Uncontrollably Happy';
		elseif ($var == '270') return 'Lots to Talk About';
		elseif ($var == '271') return 'Held-up Words Until The End';
		elseif ($var == '272') return 'Dreamstrider';
		elseif ($var == '273') return 'Ultimate Duo';
		elseif ($var == '274') return 'Glorious Number One';
		elseif ($var == '275') return 'Panesterra Conqueror';
		elseif ($var == '276') return 'Ahserion Conqueror';
		elseif ($var == '277') return 'Lord Anoha';
		elseif ($var == '278') return 'Kaldor Champion';
		elseif ($var == '279') return 'Forgotten Ruler';
		elseif ($var == '280') return 'Forgotten Conqueror';
		elseif ($var == '281') return 'Forgotten Hero';
		elseif ($var == '282') return 'Happy Anniversary to Me';
		elseif ($var == '283') return 'Beritra Resistance';
		elseif ($var == '284') return 'Mastarius\' Adversary';
		elseif ($var == '285') return 'Veille\'s Adversary';
		elseif ($var == '286') return 'Shugo Samaritan';
		else return 'None';
	}
	
	
	// Abyss ranks
	function arank($var) {
		if ($var == '1') return 'Tier 9 Abyssal Fighter';
		elseif ($var == '2') return 'Tier 8 Abyssal Fighter';
		elseif ($var == '3') return 'Tier 7 Abyssal Fighter';
		elseif ($var == '4') return 'Tier 6 Abyssal Fighter';
		elseif ($var == '5') return 'Tier 5 Abyssal Fighter';
		elseif ($var == '6') return 'Tier 4 Abyssal Fighter';
		elseif ($var == '7') return 'Tier 3 Abyssal Fighter';
		elseif ($var == '8') return 'Tier 2 Abyssal Fighter';
		elseif ($var == '9') return 'Tier 1 Abyssal Fighter';
		elseif ($var == '10') return 'Abyssal Officer &#9733;';
		elseif ($var == '11') return 'Abyssal Officer &#9733;&#9733;';
		elseif ($var == '12') return 'Abyssal Officer &#9733;&#9733;&#9733;';
		elseif ($var == '13') return 'Abyssal Officer &#9733;&#9733;&#9733;&#9733;';
		elseif ($var == '14') return 'Abyssal Officer &#9733;&#9733;&#9733;&#9733;&#9733;';
		elseif ($var == '15') return 'Abyssal General';
		elseif ($var == '16') return 'Abyssal Great General';
		elseif ($var == '17') return 'Abyssal Commander';
		elseif ($var == '18') return 'Abyssal Warlord';
		else return '???';
	}
	
	
	// Legion ranks
	function legion_rank($var) {
		if ($var == 'LEGIONARY') return 'Legionary';
		elseif ($var == 'CENTURION') return 'Centurion';
		elseif ($var == 'DEPUTY' OR $var == 'SUB_GENERAL') return 'Adjutant';
		elseif ($var == 'VOLUNTEER' OR $var == 'NEW_LEGIONARY') return 'Novice';
		elseif ($var == 'BRIGADE_GENERAL') return 'Legate';
		else return '???';
	}
	
	
	// Account access levels
	function access_level($var) {
		if ($var == '0') return 'Player';
		elseif ($var == '1') return 'Helper';
		elseif ($var == '2') return 'GM';
		elseif ($var == '3') return 'Lead GM';
		elseif ($var == '4') return 'Admin';
		elseif ($var == '5') return 'Lead Admin';
		elseif ($var == '6') return 'Lead Admin';
		else return '???';
	}
	
	
	// Account access level icons/pictures
	function access_level_ico($var) {
		if ($var == '1') return '<img src="'.Yii::app()->homeUrl.'images/top/level1.png" alt="" title="Helper" />';
		elseif ($var == '2') return '<img src="'.Yii::app()->homeUrl.'images/top/level2.png" alt="" title="GM" />';
		elseif ($var == '3') return '<img src="'.Yii::app()->homeUrl.'images/top/level3.png" alt="" title="Lead GM" />';
		elseif ($var == '4') return '<img src="'.Yii::app()->homeUrl.'images/top/level4.png" alt="" title="Admin" />';
		elseif ($var == '5') return '<img src="'.Yii::app()->homeUrl.'images/top/level5.png" alt="" title="Lead Admin" />';
		elseif ($var == '6') return '<img src="'.Yii::app()->homeUrl.'images/top/level6.png" alt="" title="Lead Admin" />';
		elseif ($var == '0') return '<img src="'.Yii::app()->homeUrl.'images/top/level0.png" alt="" title="Player" />';
		else return '???';
	}
	
	
	// Account privileges
	function membership($var) {
		if($var == '0') return 'Normal';
		elseif ($var == '1') return 'Premium';
		elseif ($var == '2') return 'VIP';
		else return $var;
	}
	
	
	// Account privilege icons/pictures
	function membership_ico($var) {
		if($var == '0') return '---';
		elseif ($var == '1') return '<img src="'.Yii::app()->homeUrl.'images/top/premium.png" alt="" title="Premium" />';
		elseif ($var == '2') return '<img src="'.Yii::app()->homeUrl.'images/top/vip.png" alt="" title="VIP" />';
		else return '???';
	}
	
	
	function status($var) {
		if ($var == 'unpaid') return '<font color="#0066cc">Unpaid</font>';
		elseif ($var == 'complete') return '<font color="#008800">Complete</font>';
		elseif ($var == 'blocked') return '<font color="#CC0000">Blocked</font>';
	}
}