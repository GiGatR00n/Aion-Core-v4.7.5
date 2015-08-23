<script type="text/javascript">
	var chart;
	jQuery(document).ready(function() {
		var colors = Highcharts.getOptions().colors,
			categories = ['Accounts', 'Characters'],
			data = [{ 
					y: <?php echo Status::accounts(); ?>,
					color: colors[1],
				}, {
					y: <?php echo Status::players(); ?>,
					color: colors[0],
				}];
		chart = new Highcharts.Chart({
			chart: {
				renderTo: 'stat', 
				type: 'column'
			},
			title: {text: 'General statistics'},
			xAxis: {categories: categories},
			yAxis: {title: {text: null}},
			plotOptions: {column: {cursor: 'pointer', showInLegend: false,},},
			tooltip: {
				formatter: function() {
					var point = this.point,
					s = this.x +':<b>'+ this.y +'</b>';
					return s;
				}
			},
			series: [{data: data, color: 'white'}],
		});
	});
	
	
	var chart1;
	jQuery(document).ready(function() {
		chart = new Highcharts.Chart({
			chart: {
				renderTo: 'online',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			colors: [
				'#FFAD36', 
				'#019CE6'
			],
			title: {
				text: 'Online: <?php echo Status::online(); ?>'
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ this.y;
				}
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false, //
						color: '#333',
						formatter: function() {
							return this.point.name +': '+ this.y;
						}
					},
					showInLegend: true,
				}
			},
			series: [{
				type: 'pie',
				name: 'Browser share',
				data: [
					['Asmodian',	<?php echo Status::online_asmo(); ?>],
					['Elyos',		<?php echo Status::online_ely(); ?>],
				]
			}]
		});
	});

	
	var chart1;
	jQuery(document).ready(function() {
		chart = new Highcharts.Chart({
			chart: {
				renderTo: 'race',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			colors: [
				'#FFAD36', 
				'#019CE6'
			],
			title: {
				text: 'Race ratio'
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ this.y;
				}
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false,//
						color: '#333',
						formatter: function() {
							return this.point.name +': '+ this.y;
						}
					},
					showInLegend: true,
				}
			},
			series: [{
				type: 'pie',
				name: 'Browser share',
				data: [
					['Asmodian', <?php echo Status::asmo(); ?>],
					['Elyos', <?php echo Status::ely(); ?>],
				]
			}]
		});
	});

	var chart1;
	jQuery(document).ready(function() {
		chart = new Highcharts.Chart({
			chart: {
				renderTo: 'legion',
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			colors: [
				'#FFAD36', 
				'#019CE6'
			],
			title: {
				text: 'Legions: <?php echo Status::legions(); ?>'
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ this.y;
				}
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false,//
						color: '#333',
						formatter: function() {
							return this.point.name +': '+ this.y;
						}
					},
					showInLegend: true,
				}
			},
			series: [{
				type: 'pie',
				name: 'Browser share',
				data: [
					['Asmodian', <?php echo Status::legions_race("ASMODIANS"); ?>],
					['Elyos', <?php echo Status::legions_race("ELYOS"); ?>],
				]
			}]
		});
	});

	
	var chart;
	jQuery(document).ready(function() {
		var colors = Highcharts.getOptions().colors,
			categories = ['Gladiator', 'Templar', 'Assassin', 'Ranger', 'Sorcerer', 'Spirit Master', 'Cleric', 'Chanter', 'Gunner', 'Raider', 'Bard'],
			data = [{ 
					y: <?php echo Status::player_class('GLADIATOR'); ?>,
					color: colors[0],
				}, {
					y: <?php echo Status::player_class('TEMPLAR'); ?>,
					color: colors[1],
				}, {
					y: <?php echo Status::player_class('ASSASSIN'); ?>,
					color: colors[2],
				}, {
					y: <?php echo Status::player_class('RANGER'); ?>,
					color: colors[3],
				}, {
					y: <?php echo Status::player_class('SORCERER'); ?>,
					color: colors[4],
				}, {
					y: <?php echo Status::player_class('SPIRIT_MASTER'); ?>,
					color: colors[5],
				}, {
					y: <?php echo Status::player_class('CLERIC'); ?>,
					color: colors[6],
				}, {
					y: <?php echo Status::player_class('CHANTER'); ?>,
					color: colors[7],
				}, {
					y: <?php echo Status::player_class('GUNNER'); ?>,
					color: colors[3],
				}, {
					y: <?php echo Status::player_class('RIDER'); ?>,
					color: colors[4],
				}, {
					y: <?php echo Status::player_class('BARD'); ?>,
					color: colors[1],


				}];
					
		
		
		
		
		chart = new Highcharts.Chart({
			chart: {
				renderTo: 'class', 
				type: 'column'
			},
			title: {
				text: 'Class statistics'
			},
			xAxis: {
				categories: categories
			},
			yAxis: {
				title: {
					text: null
				}
			},
			plotOptions: {
				column: {
					cursor: 'pointer',
					showInLegend: false,
				},
			},
			tooltip: {
				formatter: function() {
					var point = this.point,
						s = this.x +':<b>'+ this.y +'</b>';
					return s;
				}
			},
			series: [{
				data: data,
				color: 'white'
			}],

		});
	});

	
	var chart;
	jQuery(document).ready(function() {
		var colors = Highcharts.getOptions().colors,
			categories = ['0-9', '10-20', '21-30', '31-35', '36-40', '41-45', '46-50', '51-55', '56-65'],
			data = [{
					y: <?php echo Status::level(10); ?>,
					color: colors[7],
				}, {
					y: <?php echo Status::level(20); ?>,
					color: colors[6],
				}, {
					y: <?php echo Status::level(30); ?>,
					color: colors[5],
				}, {
					y: <?php echo Status::level(35); ?>,
					color: colors[4],
				}, {
					y: <?php echo Status::level(40); ?>,
					color: colors[3],
				}, {
					y: <?php echo Status::level(45); ?>,
					color: colors[2],
				}, {
					y: <?php echo Status::level(50); ?>,
					color: colors[0],
				}, {
					y: <?php echo Status::level(55); ?>,
					color: colors[1],
				}, {
					y: <?php echo Status::level(65); ?>,
					color: colors[8],
				}];
		
		chart = new Highcharts.Chart({
			chart: {
				renderTo: 'level', 
				type: 'column'
			},
			title: {
				text: 'Level statistics'
			},
			xAxis: {
				categories: categories
			},
			yAxis: {
				title: {
					text: null
				}
			},
			plotOptions: {
				column: {
					cursor: 'pointer',
					showInLegend: false,
				},
			},
			tooltip: {
				formatter: function() {
					var point = this.point,
						s = this.x +':<b>'+ this.y +'</b>';
					return s;
				}
			},
			series: [{
				data: data,
				color: 'white'
			}],

		});
	});			
</script>


<div class="note">
	<div class="note_title">
		Statistics
	</div>
	<div class="note_body center">
		<span id="stat" style="width: 330px; height: 300px; float: left"></span>
		<span id="online" style="width: 330px; height: 300px;"></span>
		<div class="menu_line"></div>
		
		<span id="race" style="width: 330px; height: 300px; float: left"></span>
		<span id="legion" style="width: 330px; height: 300px;"></span>
		<div class="menu_line"></div>
		
		<div id="class" style="width: 660px; height: 350px; margin: 0 auto"></div>
		<div class="menu_line"></div>
		
		<div id="level" style="width: 660px; height: 350px; margin: 0 auto"></div>
	</div>
</div>