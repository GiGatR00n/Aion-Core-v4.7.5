






<div class="note mb15">
	<div class="note_title">
		Find a player
	</div>
	<div class="note_body">
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		<table class="table_info">
			<tr>
				<td width="200px"><label for="SearchForm_name">Character name</label></td>
				<td><input type="text" id="SearchForm_name" name="SearchForm[name]"></td>
			</tr>
			<tr>
				<td><?php $this->widget('CCaptcha', array('showRefreshButton' => false, 'clickableImage' => true)); ?></td>
				<td><?php echo CHtml::activeTextField($form,'captcha') ?></td>
			</tr>
			<tr>
				<td></td>
				<td><input class="button" type="submit" value="Search" name="yt0"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>


<?php if ($model): ?>
<div class="note">
	<div class="note_title">
		Character list
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Name</td>
				<th width="95px">Level</td>
				<th width="95px">AP</td>
				<th width="95px">GP</td>
				<th width="95px">Race</td>
				<th width="95px">Class</td>
				<th width="95px">Status</td>
			</tr>
			<?php foreach ($model as $model): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$model->name; ?>"><?php echo $model->name; ?></a></td>
				<td><?php echo Info::lvl($model->exp); ?></td>
				<td><?php echo number_format($model->abyssRank->ap,0,' ',' '); ?></td>
				<td><?php echo number_format($model->abyssRank->gp,0,' ',' '); ?></td>
				<td><?php echo Info::race($model->race) ?></td>
				<td><?php echo Info::player_class($model->player_class) ?></td>
				<td><?php echo Info::online($model->online); ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>
<?php endif; ?>