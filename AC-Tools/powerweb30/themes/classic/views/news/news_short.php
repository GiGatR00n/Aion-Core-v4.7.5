






<?php foreach ($model as $model): ?>
<div class="note mb15">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl.'news/'.$model->news_id; ?>"><?php echo $model->title; ?></a>
		<?php if (!Yii::app()->user->isGuest AND Yii::app()->user->access_level >= Config::get('access_level_editor')): ?><a class="edit" title="Edit" href="<?php echo Yii::app()->homeUrl.'news/edit/'.$model->news_id; ?>"></a><?php endif; ?>
	</div>
	<div class="note_body h15 img">
		<?php echo $model->short_story; ?>
	</div>
	<div class="note_info clear">
		<a href="<?php echo Yii::app()->homeUrl.'news/'.$model->news_id; ?>">Read more &rarr;</a>
		<div class="right img_fix">
			<img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/user.png" alt="" /> <?php echo $model->author->name; ?> |
			<img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/calendar.png" alt="" /> <?php echo Yii::app()->dateFormatter->format('dd.M.y, HH:mm:ss', $model->date); ?> |
			<img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/category.png" alt="" /> <a href="<?php echo Yii::app()->homeUrl.'category/'.$model->category['alt_name']; ?>"><?php echo $model->category['name']; ?></a>
		</div>
	</div>
</div>
<?php endforeach; ?>


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