<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Пополнение баланса</div>
		<table class="items">
			<tr>
				<th>№ платежа</th>
				<th>Сумма</th>
				<th>Аккаунт</th>
				<th>Дата</th>
				<th>Статус</th>
				<th>Система</th>
			</tr>
		<?php foreach ($model as $model): ?>
			<tr class="center">
				<td><?php echo $model->pay_id; ?></td>
				<td><?php echo $model->sum; ?></td>
				<td><?php echo $model->account; ?></td>
				<td><?php echo $model->date; ?></td>
				<td><?php echo $model->status; ?></td>
				<td><?php echo $model->system; ?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>