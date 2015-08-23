<div class="note mb15">
	<div class="note_body">
		<div class="note_title">Список персонажей</div>
		<table class="items">
			<tr>
				<th>Имя</th>
				<th width="95px">Уровень</th>
				<th width="95px">Раса</th>
				<th width="95px">Класс</th>
				<th width="150px">Создан</th>
			</tr>
			<?php foreach ($model as $model): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$model->name; ?>"><?php echo $model->name; ?></a></td>
				<td><?php echo Info::lvl($model->exp); ?></td>
				<td><?php echo Info::race($model->race) ?></td>
				<td><?php echo Info::player_class($model->player_class) ?></td>
				<td><?php echo $model->creation_date; ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>

<?php if ($referals): ?>
<div class="note">
	<div class="note_body">
		<div class="note_title">Список персонажей рефералов</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<table class="table_top">
			<tr class="bold center">
				<th>Имя</th>
				<th width="95px">Уровень</th>
				<th width="95px">Раса</th>
				<th width="95px">Класс</th>
				<th width="140px">Дата создания</th>
				<th width="95px">Бонус</th>
			</tr>
		<?php foreach ($referals as $referal): ?>
			<tr class="center">
				<td><?php echo $referal->name; ?></td>
				<td><?php echo Info::lvl($referal->exp); ?></td>
				<td><?php echo Info::race($referal->race); ?></td>
				<td><?php echo Info::player_class($referal->player_class); ?></td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.M.y, HH:mm:ss', $referal->creation_date); ?></td>
				<td><?php if(Info::lvl($referal->exp) >= 50): ?><input type="radio" id="LogReferals_slave_id" name="LogReferals[slave_id]" value="<?php echo $referal->account_id; ?>" /><?php endif; ?></td>
			</tr>
		<?php endforeach ?>
			<tr class="center">
				<td colspan="6"><input type="submit" name="yt0" value="Получить бонус" class="button"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>
<?php endif; ?>