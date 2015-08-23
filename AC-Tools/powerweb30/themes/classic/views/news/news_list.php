






<div class="note mb15">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; News articles
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th width="80px">Date</th>
				<th>Title</th>
				<th>Category</th>
				<th>Author</th>
				<th colspan="2">Edit/delete</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y', $data->date); ?></td>
				<td align="left"><a href="<?php echo Yii::app()->homeUrl.'news/'.$data->news_id; ?>"><?php echo $data->title; ?></a></td>
				<td><a href="<?php echo Yii::app()->homeUrl.'category/'.$data->category->name; ?>"><?php echo $data->category->name; ?></a></td>
				<td><?php echo $data->author->name; ?></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'news/edit/'.$data->news_id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/edit.png" title="Edit" /></a></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'news/delete/'.$data->news_id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/delete.png" title="Delete" /></a></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>