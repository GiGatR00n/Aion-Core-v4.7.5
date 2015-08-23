






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Report - Balance top-ups
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Payment number</th>
				<th>Total</th>
				<th>Account</th>
				<th>Date</th>
				<th>Status</th>
				<th>System</th>
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
	<?php $this->widget('CLinkPager',array(
		'pages'=>$pages,
		'maxButtonCount' => 7,
		'nextPageLabel' => '&rarr;',
		'prevPageLabel' => '&larr;',
		'firstPageLabel' => '<<',
		'lastPageLabel' => '>>',
		'header' => '',
	)); ?>
</div>