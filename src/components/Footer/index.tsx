import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';

const Footer: React.FC = () => {
  const defaultMessage = '北辰';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'xiao',
          title: '联系小张',
          href: 'https://wpa.qq.com/msgrd?v=3&uin=960551423&site=qq&menu=yes',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/',
          blankTarget: true,
        },
        {
          key: 'zhang',
          title: '联系小张',
          href: 'https://wpa.qq.com/msgrd?v=3&uin=960551423&site=qq&menu=yes',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
