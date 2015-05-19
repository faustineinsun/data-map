- Template from [https://github.com/faustineinsun/scalaJS-playground](https://github.com/faustineinsun/scalaJS-playground)

### On local [http://localhost:5000/](http://localhost:5000/)

```
$ npm install
$ foreman start web 
```

### On remote machine

* [AWS EC2](http://ec2-52-6-112-107.compute-1.amazonaws.com:5000/)

### Submit modifications

* Fetch data from `main-repo-master-branch` to `your-repo-master-branch`

* Fetch and merge code from your `master` branch to `frontend` branch

```
$ git checkout master 
$ git pull
$ git checkout -b frontend
$ git merge master

```

* Modify codes in branch `frontend` -> folder `hadoophackathon/frontend-nodejs-webgl` 

```
$ vim views/index.html
$ vim public/js/webgl/webglmaptangram.js
$ vim public/css/webgl/webglmap.css
$ vim public/assets/scene.yaml
$ vim public/assets/analyzeddata-final.json
```

* Submit modifications

```
$ git add --all
$ git commit -m "some notes" 
$ git push origin frontend
```

* [submit pull request](https://www.atlassian.com/git/tutorials/making-a-pull-request/how-it-works)
    * from `your-repo-frontend-branch` to `main-repo-master-branch`
