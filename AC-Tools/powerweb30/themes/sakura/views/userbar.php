<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/jquery.xslider.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
  $('#slide').xslider({
    timeout: 3000,
    effect: 'roll',
    navigation: true,
    onLoaded: function(current, last, currentItem, lastItem, elements) {
      $('.caption', currentItem).fadeIn(200);
	  document.getElementById('fon').value=current+1;
    },
    onBefore: function(current, last, currentItem, lastItem, elements) {
      $('.caption', lastItem).hide();
      $('.caption', currentItem).hide();
	  document.getElementById('fon').value=current+1;
    },
    onComplete: function(current, last, currentItem, lastItem, elements) {
      $('.caption', currentItem).fadeIn(200);
	  document.getElementById('fon').value=current+1;
    }
  });
});
</script>

<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/iColorPicker.js"></script>
<script>
	function select_field (field) {field.focus(); field.select();}
</script>
	
<div class="note mb15">
	<div class="note_body">
		<div class="note_title">Создание юзербара</div>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($model)?></div>
		<table class="table_info">
			<tr>
				<td width="200px">Персонаж</td>
				<td>
					<select name="UserbarForm[player_id]" id="UserbarForm_player_id">
						<?php foreach ($players as $player): ?>
						<option value="<?php echo $player->id; ?>"><?php echo $player->name; ?></option>
						<?php endforeach; ?>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="bold">Выберите цвет</td>
			</tr>
			<tr>
				<td>Имя</td>
				<td><input class="iColorPicker" type="text" name="UserbarForm[name]" id="UserbarForm_name" value="#ffcc00"></td>
			</tr>
			<tr>
				<td>Легион</td>
				<td><input class="iColorPicker" type="text" name="UserbarForm[legion]" id="UserbarForm_legion" value="#00aeef"></td>
			</tr>
			<tr>
				<td>Раса, класс</td>
				<td><input class="iColorPicker" type="text" name="UserbarForm[class_race]" id="UserbarForm_class_race" value="#ffffff"></td>
			</tr>
			<tr>
				<td>Уровень</td>
				<td><input class="iColorPicker" type="text" name="UserbarForm[level]" id="UserbarForm_level" value="#ff3300"></td>
			</tr>
			<tr>
				<td>PvP</td>
				<td><input class="iColorPicker" type="text" name="UserbarForm[kills]" id="UserbarForm_kills" value="#00aeef"></td>
			</tr>
			<tr>
				<td>Жизни, мана</td>
				<td><input class="iColorPicker" type="text" name="UserbarForm[stats]" id="UserbarForm_stats" value="#acd372"></td>
			</tr>
			<tr>
				<td>Тень</td>
				<td><input class="iColorPicker" type="text" name="UserbarForm[shadow]" id="UserbarForm_shadow" value="#000000"></td>
			</tr>
			<tr>
				<td colspan="2" class="bold">Выберите фон</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="slide-wrapper">
						<ul id="slide">
						<li><img id="1" src="images/userbars/ub1.png" width="400" height="80" /></li>
						<li><img id="1" src="images/userbars/ub2.png" width="400" height="80" /></li>
						<li><img id="1" src="images/userbars/ub3.png" width="400" height="80" /></li>
						<li><img id="1" src="images/userbars/ub4.png" width="400" height="80" /></li>
						<li><img id="1" src="images/userbars/ub5.png" width="400" height="80" /></li>
						<li><img id="1" src="images/userbars/ub6.png" width="400" height="80" /></li>
						<li><img id="1" src="images/userbars/ub7.png" width="400" height="80" /></li>
						</ul>
						<input type="hidden" id="fon" name="UserbarForm[fon]" />
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="center"><input class="button" type="submit" value="Создать юзербар" name="yt0"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>

<?php if ($image): ?>
<div class="note">
	<div class="note_body">
		<div class="note_title">Ссылки на юзербар</div>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($model)?></div>
		<table class="table_info">
			<tr>
				<td width="200px">Результат</td>
				<td><img src="<?php echo $image; ?>" /></td>
			</tr>
			<tr>
				<td>Ссылка на кртанику</td>
				<td><?php echo $image; ?></td>
			</tr>
			<tr>
				<td>HTML код</td>
				<td><input size="62" type="text" value="&lt;a href=&#34<?php echo Yii::app()->homeUrl; ?>&#34;>&lt;img src=&#34;<?php echo $image; ?>&#34; /&gt;&lt;/a&gt;" onClick="select_field(this);"></td>
			</tr>
			<tr>
				<td>[BB] код</td>
				<td><input size="62" type="text" value="[url=<?php echo Yii::app()->homeUrl; ?>][img]<?php echo $image; ?>[/img][/url]" onClick="select_field(this);"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>
<?php endif; ?>