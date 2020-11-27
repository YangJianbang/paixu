public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private RBNode root;

    public RBNode getRoot() {
        return root;
    }

    public void setRoot(RBNode root) {
        this.root = root;
    }

    //删除节点之获取节点后删除
    public V remove(K key) {
        RBNode node = getNode(key);
        if (node == null) {
            return null;
        }
        V oldValue = (V) node.value;
        deleteNode(node);
        return oldValue;
    }

    /**
     * 删除节点：
     * 1.删除叶子结点-》直接删除
     * 2.删除只有一个子节点的节点，用子节点代替
     * 3.删除有两个子节点的节点，找到前驱或后继节点
     *
     * @param node
     */
    private void deleteNode(RBNode node) {
        //3.双子节点
        if (node.left != null && node.right != null) {
            //获取删除节点的后继节点
            RBNode sucessor = sucessor(node);
//            获取前驱节点
//            RBNode predecessor = predecessor(node);
            node.key = sucessor.key;
            node.value = sucessor.value;
            //后继节点赋为node,此时的后继节点为node
            node = sucessor;
        }
//        替代节点
        //单子节点
        RBNode replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            //替代者的父节点指针指向删除节点的父节点
            replacement.parent = node.parent;
            if (node.parent == null) {
                //node为根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
//            将node的指针全部清空，进入游离状态等待垃圾回收
            node.left = null;
            node.right = null;
            node.parent = null;
            //替换后调整平衡
            if (node.color == BLACK) {
                //替代节点一定是红色（此时需要变色）
                fixAfterRemove(replacement);
            }
        } else if (node.parent == null) {
            //删除的节点是根节点
            root = null;
        } else {
            //1. 叶子结点
            if (node.color == BLACK) {
                fixAfterRemove(node);

            }
            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else if (node == node.parent.right) {
                    node.parent.right = null;
                }
                node.parent = null;
            }
        }
    }

    /**
     * 删除调整
     * 1.自己能搞定：单子节点
     * 2.自己搞不定：
     * 2.1向兄弟节点借，兄弟不借，向父亲节点借，父亲下来，兄弟上去
     * 2.2向兄弟节点借，兄弟没有可借子节点可借
     *
     * @param x
     */
    private void fixAfterRemove(RBNode x) {
        //情况2
        while (x != root && x.color == BLACK) {
            //x在左边
            if (x == leftOf(parentOf(x))) {
                //兄弟节点
                RBNode rNode = rightOf(parentOf(x));
                //判断是否是真正的兄弟节点（即对应的234树上的兄弟节点）
                if (colorOf(rNode) == RED) {
                    //颜色为红则进行调整寻找真正的兄弟节点
                    setColor(rNode, BLACK);
                    setColor(parentOf(x), RED);
                    leftRotate(parentOf(x));
                    //找到真正的兄弟节点
                    rNode = rightOf(parentOf(x));
                }
//            2.2 (此时的BLACK表示为NULL)
//            private boolean colorOf(RBNode node) {
//             return node == null ? BLACK : node.color;
//           }
                if (colorOf(leftOf(rNode)) == BLACK && colorOf(rightOf(rNode)) == BLACK) {
                    setColor(rNode, RED);
                    x = parentOf(x);
                } else {
//            2.1   兄弟节点为3节点或者4节点
                    if (colorOf(rightOf(rNode)) == BLACK) {
                        setColor(leftOf(rNode), BLACK);
                        setColor(rNode, RED);
                        rightRotate(rNode);
                        rNode = rightOf(parentOf(x));
                    }
                    setColor(rNode, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(rNode), BLACK);
                    leftRotate(parentOf(x));
                    x = root;
                }
            } else {
                //x在右边
                //兄弟节点
                RBNode rNode = leftOf(parentOf(x));
                //判断是否是真正的兄弟节点（即对应的234树上的兄弟节点）
                if (colorOf(rNode) == RED) {
                    //颜色为红则进行调整寻找真正的兄弟节点
                    setColor(rNode, BLACK);
                    setColor(parentOf(x), RED);
                    rightRotate(parentOf(x));
                    //找到真正的兄弟节点
                    rNode = leftOf(parentOf(x));
                }
//            2.2 (此时的BLACK表示为NULL)
//            private boolean colorOf(RBNode node) {
//             return node == null ? BLACK : node.color;
//           }
                if (colorOf(leftOf(rNode)) == BLACK && colorOf(rightOf(rNode)) == BLACK) {
                    setColor(rNode, RED);
                    x = parentOf(x);
                } else {
//            2.1   兄弟节点为3节点或者4节点
                    if (colorOf(leftOf(rNode)) == BLACK) {
                        setColor(rightOf(rNode), BLACK);
                        setColor(rNode, RED);
                        leftRotate(rNode);
                        rNode = leftOf(parentOf(x));
                    }
                    setColor(rNode, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(rNode), BLACK);
                    rightRotate(parentOf(x));
                    x = root;
                }
            }
        }
//      情况1
        setColor(x, BLACK);
    }

    //获取节点
    private RBNode getNode(K key) {
        RBNode node = root;
        while (node != null) {
            int cmp = key.compareTo((K) node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    //调整
    /*
     * 1.两个节点，上红下黑不需要调整
     *     3
     *    /
     *   2
     * 2.三个节点，左边三个||右边三个需要调整 ，左中右不需要调整
     *       3     3               2      2.5
     *      /     /               / \     / \
     *     2     2       =>      1   3   2   3
     *    /       \
     *   1        2.5
     * 3.四个节点，新增节点+爷爷节点黑，父节点、叔叔节点为红=爷爷节点变红，父节点叔叔节点变黑。爷爷节点为根节点变黑
     *       2
     *      / \
     *     1   3
     *          \
     *           4
     */
    private void fixAfterPut(RBNode x) {
        x.color = RED;
        while (x != null && x != root && colorOf(parentOf(x)) == RED) {
//            左三
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                RBNode y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        leftRotate(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rightRotate(parentOf(parentOf(x)));
                }
            } else {
                RBNode y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rightRotate(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    leftRotate(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    //    添加方法
    public void put(K key, V value) {
        //获取根节点
        RBNode t = this.root;
        if (t == null) {
            root = new RBNode<>(key, value == null ? key : value, null);
            return;
        }
        int cmp;
        //双亲指针
        RBNode parant;
        if (key == null) {
            throw new NullPointerException();
        }
        do {
            parant = t;
            cmp = key.compareTo((K) t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                t.setValue(value == null ? key : value);
                return;
            }
        } while (t != null);

        RBNode<K, Object> node = new RBNode<>(key, value == null ? key : value, parant);

        if (cmp < 0) {
            parant.left = node;
        } else {
            parant.right = node;
        }
        fixAfterPut(node);
    }

    /*         3
     *        /            2
     *       2   =>      /  \
     *      /           1    3
     *     1
     * */
    //右旋方法
    private void rightRotate(RBNode p) {
        //判断节点是否为空
        if (p != null) {
//            获取P节点的左节点
            RBNode l = p.left;
//            将P节点指向l的右节点
            p.left = l.right;
            if (l.right != null) {
//                将l节点的右节点指向p节点
                l.right.parent = p;
            }
//            将l的父节点指向p的父节点
            l.parent = p.parent;
            if (p.parent == null) {
//                p的父节点为空则表示p为根节点，右旋时l将变为根节点
                root = l;
            } else if (p.parent.left == p) {
//                判断p在p父节点的哪边并把父节点指向l
                p.parent.left = l;
            } else {
//                同上
                p.parent.right = l;
            }
//            将p的父节点指向l
            p.parent = l;
//            将l的右节点指向p
            l.right = p;
        }
    }

    //左旋方法
    /*   3
     *    \              4
     *     4    =>     /  \
     *      \         3    5
     *       5
     * */
    private void leftRotate(RBNode p) {
        if (p != null) {
            RBNode r = p.right;
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }
            r.parent = p.parent;
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            p.parent = r;
            r.left = p;
        }
    }

    private void setColor(RBNode node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    /**
     * 获取后继节点（在比node大的节点中最小 的节点）
     *
     * @param node
     * @return
     */
    private RBNode sucessor(RBNode node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            RBNode p = node.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        } else {
            RBNode parent = node.parent;
            RBNode child = node;
            while (parent != null && parent.right == child) {
                child = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    /**
     * 获取前驱节点（在比node小的节点中最大的节点）
     *
     * @param node
     * @return
     */
    private RBNode predecessor(RBNode node) {
        if (node == null) {
            return null;
        } else if (node.left != null) {
            RBNode p = node.left;
            while (p.right != null) {
                p = p.right;
            }
            return p;
        } else {
            RBNode parent = node.parent;
            RBNode child = node;
            while (parent != null && parent.left == child) {
                child = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    private boolean colorOf(RBNode node) {
        return node == null ? BLACK : node.color;
    }

    private RBNode parentOf(RBNode node) {
        return node != null ? node.parent : null;
    }

    private RBNode leftOf(RBNode node) {
        return node != null ? node.left : null;
    }

    private RBNode rightOf(RBNode node) {
        return node != null ? node.right : null;
    }

    //红黑树节点属性
    static class RBNode<K extends Comparable<K>, V> {
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K key;
        private V value;

        public RBNode(K key, V value, RBNode parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.color = BLACK;
        }

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }

        public RBNode() {

        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
